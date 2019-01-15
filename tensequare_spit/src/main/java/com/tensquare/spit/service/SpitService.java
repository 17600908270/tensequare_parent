package com.tensquare.spit.service;

import com.tensquare.spit.dao.SpitDao;
import com.tensquare.spit.pojo.Spit;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class SpitService {

    @Autowired
    private SpitDao spitDao;
    @Autowired
    private  IdWorker idWorker;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private RedisTemplate  redisTemplate;

    public List<Spit> findAll(){
        return spitDao.findAll();
    }

    public Spit    findOne(String id){

        return spitDao.findById(id).get();
    }

    public void insertSpit(Spit spit){
        spit.set_id(idWorker.nextId()+"");
        spit.setPublishtime(new Date());

        spit.setVisits(0);//浏览量
        spit.setShare(0);//分享数
        spit.setThumbup(0);//点赞数
        spit.setComment(0);//回复数
        spit.setState("1");//状态
        if(spit.getParentid()!=null && !"".equals(spit.getParentid())) {

            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(spit.getParentid()));

            Update update = new Update();
            update.inc("comment", 1);
            mongoTemplate.updateFirst(query, update, "spit");
        }
        spitDao.save(spit);

    }

    public void  updateSpit(Spit spit){
        spitDao.save(spit);
    }
    public  void delete(String id){
        spitDao.deleteById(id);
    }

    public Page<Spit> findByParentId(String parentId,int page,int size){

        PageRequest pageable= PageRequest.of(page-1,size);


        return  spitDao.findByParentid(parentId,pageable);
    }

    public Result addThumbup(String spitId) {
      /*  Spit spit = spitDao.findById(spitId).get();
        spit.setThumbup(((spit.getThumbup() == null) ? 0 : spit.getThumbup()) + 1);
        spitDao.save(spit);*/

      //db.spit.update({"_id":"1"},{$inc:{"thumbup":NumberInt(1)}})

        String userid="11";

        if(redisTemplate.opsForValue().get("thumbup_"+userid)!=null){
            return new Result(false,StatusCode.REMOTEERROR,"不能重复点赞");
        }
        Query query=new Query();
        query.addCriteria(Criteria.where("_id").is(spitId));

        Update update=new Update();
        update.inc("thumbup",1);
        mongoTemplate.updateFirst(query, update,"spit");
        redisTemplate.opsForValue().set("thumbup_"+userid,"1");
        return new Result(true, StatusCode.OK,"点赞成功") ;

    }
}

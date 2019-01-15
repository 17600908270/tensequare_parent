package com.tensquare.spit.controller;

import com.tensquare.spit.pojo.Spit;
import com.tensquare.spit.service.SpitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/spit")
public class SpitController {

    @Autowired
    private SpitService spitService;

    @RequestMapping(method = RequestMethod.GET)
    public Result  findAll(){
        return  new Result(true, StatusCode.OK,"查询成功",spitService.findAll());
    }
    @RequestMapping(value = "/{spitId}",method = RequestMethod.GET)
    public Result  findOne(@PathVariable(value = "spitId") String id){
        return new Result(true,StatusCode.OK,"查询成功",spitService.findOne(id));
    }
    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody  Spit spit){
        spitService.insertSpit(spit);
        return  new Result(true,StatusCode.OK,"添加成功");
    }
    @RequestMapping(value = "/{spitId}",method = RequestMethod.PUT)
    public Result  updateSpit(@PathVariable(value ="spitId")  String id,@RequestBody Spit spit){
        spit.set_id(id);
        spitService.updateSpit(spit);
        return  new Result(true,StatusCode.OK,"修改成功");
    }
    @RequestMapping(value = "/{spitId}",method = RequestMethod.DELETE)
    public Result  deleteSpit(@PathVariable(value = "spitId")  String id){
        spitService.delete(id);
        return  new Result(true,StatusCode.OK,"删除成功");
    }

    @RequestMapping(value = "/comment/{parentid}/{page}/{size}",method = RequestMethod.GET)
    public Result  findByParentId(@PathVariable(value = "parentid") String parentId,@PathVariable int page,@PathVariable int size){

        Page<Spit> byParentId = spitService.findByParentId(parentId, page, size);
        return new Result(true,StatusCode.OK,"查询成功",new PageResult<Spit>(byParentId.getTotalElements(),byParentId.getContent()));
    }

    @RequestMapping(value = "/thumbup/{spitId}",method = RequestMethod.PUT)
    public Result addThumbup(@PathVariable String spitId){

        return  spitService.addThumbup(spitId);
    }

}

package com.kelaker.kcommon.system.api.manage;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kelaker.kcommon.system.dto.SysDictDto;
import com.kelaker.kcommon.system.service.SysDictService;
import com.kelaker.kcommon.system.vo.SysDictVo;
import com.kelaker.ktools.common.vo.RequestPage;
import com.kelaker.ktools.web.annotation.HasAction;
import com.kelaker.ktools.web.annotation.InModule;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;

/**
 * 系统字典
 *
 * @author felix huang
 * @since 2020-03-27 11:42:57
 */
@RestController
@RequestMapping("/system/manage/dict")
@InModule(moduleCode = "SYSTEM")
public class SysDictManageApi {

    /**
     * 服务对象
     */
    @Resource
    private SysDictService sysDictService;

    /**
     * 分页查询所有字典
     *
     * @param params
     */
    @HasAction(actionCode = "SYS_DICT:PAGE",actionName = "系统字典列表")
    @PostMapping("/page")
    public IPage<SysDictVo> selectListByDictPage(@RequestBody RequestPage<SysDictDto> params) {
        return sysDictService.selectListByDictPage(params);
    }

    /**
     * 根据字典代码查询
     *
     * @param dictCode 字典代码
     */
    @GetMapping("/get/{dictCode}")
    public SysDictVo getSysDictByCode(@Validated @NotBlank(message = "配置Key不能为空") @PathVariable("dictCode") String dictCode) {
        return sysDictService.getSysDictByCode(dictCode);
    }

    /**
     * 编辑字典
     *
     * @param sysDictDto 字典数据
     */
    @HasAction(actionCode = "SYS_DICT:UPDATE",actionName = "修改系统字典")
    @PutMapping("/update")
    public void updateSysDict(@RequestBody SysDictDto sysDictDto) {
        sysDictService.updateSysDict(sysDictDto);
    }

    /**
     * 添加字典
     *
     * @param sysDictDto dto
     */
    @HasAction(actionCode = "SYS_DICT:ADD",actionName = "添加系统字典")
    @PostMapping("/add")
    public void addSysDict(@Validated @RequestBody SysDictDto sysDictDto) {
        sysDictService.addSysDict(sysDictDto);
    }

    /**
     * 根据分组代码查询字典列表-树状结构
     *
     * @param groupCode 分组代码
     */
    @GetMapping("/list/{groupCode}")
    public List<SysDictVo> getListByGroupCode(@PathVariable("groupCode") String groupCode) {
        return sysDictService.listDict(groupCode);
    }

    /**
     * 根据所有字典列表
     */
    @GetMapping("/list/all")
    public List<SysDictVo> findAllList() {
        return sysDictService.findAllSysDict();
    }

    /**
     * 获取所有分组代码
     */
    @GetMapping("/get/all/group")
    public Set<String> getAllGroupCode() {
        return sysDictService.getAllGroupCode();
    }

    /**
     * 根据分组代码查询字典列表-非树状结构
     *
     * @param groupCode 分组代码
     */
    @GetMapping("/list/not/tree/{groupCode}")
    public List<SysDictVo> getListByGroupCodeNotTree(@Validated @NotBlank(message = "分组代码不能为空") @PathVariable("groupCode") String groupCode) {
        return sysDictService.findVoListByDictGroupCode(groupCode);
    }

}
package com.koko.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.koko.domain.ResponseResult;
import com.koko.domain.dto.UserAddDto;
import com.koko.domain.dto.UserUpdateDto;
import com.koko.domain.entity.Role;
import com.koko.domain.entity.User;
import com.koko.domain.entity.UserRole;
import com.koko.domain.vo.*;
import com.koko.enums.AppHttpCodeEnum;
import com.koko.exception.SystemException;
import com.koko.mapper.UserMapper;
import com.koko.service.RoleService;
import com.koko.service.UserRoleService;
import com.koko.service.UserService;
import com.koko.utils.BeanCopyUtils;
import com.koko.utils.SecurityUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2023-03-07 21:06:57
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseResult userInfo() {
        //获取当前用户id
        Long userId = SecurityUtils.getUserId();
        //根据用户id查询用户信息
        User user = getById(userId);
        //封装成UserInfoVo
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(userInfoVo);
    }

    @Override
    public ResponseResult updateUserInfo(User user) {
        updateById(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult register(User user) {
        //对数据进行非空判断
        if(!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getPassword())){
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }
        //对数据进行是否存在的判断
        if(userNameExist(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if(nickNameExist(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        //对密码进行加密
        String encode = passwordEncoder.encode(user.getPassword());
        user.setPassword(encode);
        //存入数据库
        save(user);
        return ResponseResult.okResult();
    }

    /**
     * 列出所有用户
     * @param pageNum
     * @param pageSize
     * @param userName
     * @param phonenumber
     * @param status
     * @return
     */
    @Override
    public ResponseResult userAllList(Integer pageNum, Integer pageSize, String userName, String phonenumber, String status) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Strings.isNotEmpty(userName),User::getUserName,userName)
                    .like(Strings.isNotEmpty(phonenumber),User::getPhonenumber,phonenumber)
                    .like(Strings.isNotEmpty(status),User::getStatus,status);
        //分页
        Page<User> page = new Page(pageNum,pageSize);
        page(page,queryWrapper);
        //封装
        List<UserListVo> userListVos = BeanCopyUtils.copyBeanList(page.getRecords(), UserListVo.class);
        PageVo pageVo = new PageVo(userListVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }


    @Autowired
    private UserRoleService userRoleService;

    /**
     * 添加用户
     * @param userAddDto
     * @return
     */
    @Override
    public ResponseResult addUser(UserAddDto userAddDto) {
        //对密码加密
        String encode = passwordEncoder.encode(userAddDto.getPassword());
        userAddDto.setPassword(encode);
        User user = BeanCopyUtils.copyBean(userAddDto, User.class);
        //添加用户
        save(user);
        //查询添加用户后的id
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,user.getUserName());
        User one = getOne(queryWrapper);
        Long userId = one.getId();
        //给用户角色表添加
        return userRoleService.addUserRole(userId,userAddDto.getRoleIds());
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @Override
    public ResponseResult deleteUser(Long id) {
        if(SecurityUtils.getUserId().equals(id)){
            return ResponseResult.errorResult(999,"不能删除当前操作的用户");
        }
        removeById(id);
        //删除用户角色表
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUserId,id);
        userRoleService.remove(queryWrapper);
        return ResponseResult.okResult();
    }

    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;

    /**
     * 修改用户回显用户信息
     * @param id
     * @return
     */
    @Override
    public ResponseResult returnViewUser(Long id) {
        //查询用户所关联的角色id列表
        List<String> roleIds = userRoleService.getUserRole(id);
        //所有角色的列表
        List<RoleAddVo> roleAddVos = (List<RoleAddVo>) roleService.listAllRole().getData();
        //查询用户信息
        User user = getById(id);
        UserReturnInfoVo userReturnInfoVo = BeanCopyUtils.copyBean(user, UserReturnInfoVo.class);
        //封装
        UserReturnViewVo userReturnViewVo = new UserReturnViewVo(roleIds,roleAddVos,userReturnInfoVo);
        return ResponseResult.okResult(userReturnViewVo);
    }

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    @Override
    public ResponseResult updateUser(UserUpdateDto user) {
        //更新用户表
        User updateUser = BeanCopyUtils.copyBean(user, User.class);
        userService.saveOrUpdate(updateUser);
        //更新用户角色表
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUserId,user.getId());
        userRoleService.remove(queryWrapper);
        userRoleService.addUserRole(user.getId(),user.getRoleIds());
        return ResponseResult.okResult();
    }

    /**
     * 判断昵称是否存在
     * @param nickName
     * @return
     */
    private boolean nickNameExist(String nickName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getNickName,nickName);
        return count(queryWrapper)>0;
    }

    /**
     * 判断用户名是否存在
     * @param userName
     * @return
     */
    private boolean userNameExist(String userName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,userName);
        return count(queryWrapper)>0;
    }
}

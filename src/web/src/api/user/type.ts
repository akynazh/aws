export interface LoginParams {
  uid: string;
  password: string;
}

export interface LoginResultModel {
  code: number;
  msg: string;
  data: string;
}

interface UserInfo {
    /**
     * 创建时间，毫秒级时间戳
     */
    createTime?: number;
    /**
     * 用户 ID
     */
    id?: number;
    /**
     * 员工姓名
     */
    name?: string;
    /**
     * 角色，以英文逗号分隔，比如 ROLE_EMPLOYEE,ROLE_ADMIN
     */
    roles?: string;
    /**
     * 状态，0 为禁用，1 为启用，2 为已删除
     */
    status?: number;
    /**
     * 用户身份证
     */
    uid?: string;
    /**
     * 更新时间，毫秒级时间戳
     */
    updateTime?: number;
    [property: string]: any;
}

export interface UserInfoModel {
  code: number;
  msg: string;
  data: UserInfo;
}

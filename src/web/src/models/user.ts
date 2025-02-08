export interface UserWorkOutputVO {
  name: string;
  workId: number;
  produceName: string;
  dataValue: number;
  unit: string;
}

export interface UserUpdateVO {
  uid: string;
  name: string;
  password: string;
  status: number;
  roles: string;
}

export interface UserVO {
  id: number;
  uid: string;
  name: string;
  roles: string;
  createTime: number;
  updateTime: number;
  status: number;
}

export interface UserUpdateMeVO {
  name: string;
  password: string;
}

export interface UserRegisterVO {
  uid: string;
  name: string;
  roles: string;
}

export interface UserLoginVO {
  uid: string;
  password: string;
}

export interface UserListVO {
  userList: UserVO[];
  count: number;
}

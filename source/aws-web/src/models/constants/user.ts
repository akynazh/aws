export const UserStatus = {
  DISABLED: 0,
  ENABLE: 1,
  DELETED: 2
} as const;

export const UserStatusMap = {
  [UserStatus.DISABLED]: "禁用",
  [UserStatus.ENABLE]: "启用",
  [UserStatus.DELETED]: "已删除"
} as const;

export const UserRole = {
  ADMIN: "ROLE_ADMIN",
  EMPLOYEE: "ROLE_EMPLOYEE",
  SCALE: "ROLE_SCALE",
} as const;

export const UserRoleMap = {
  [UserRole.ADMIN]: "管理员",
  [UserRole.EMPLOYEE]: "员工",
  [UserRole.SCALE]: "电子秤",
} as const;

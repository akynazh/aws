export interface WorkReassignVO {
  workId: number;
  employeeIdList: number[];
}

export interface WorkAssignmentsVO {
  workId: number;
  employees: { [key: string]: string };
}

export interface WorkAssignVO {
  workId: number;
  employeeIdList: number[];
}

export interface MyAssignmentsVO {
  workIds: number[];
}

export interface WorkUpdateVO {
  id: number;
  produceId: number;
  startTime: number;
  endTime: number;
  dataValue: number;
  unit: number;
  status: number;
}

export interface WorkVO {
  id: number;
  produceId: number;
  startTime: number;
  endTime: number;
  dataValue: number;
  unit: number;
  createTime: number;
  updateTime: number;
  status: number;
}

export interface WorkAddVO {
  produceId: number;
  startTime: number;
  endTime: number;
}

export interface WorkListVO {
  workList: WorkVO[];
  count: number;
}

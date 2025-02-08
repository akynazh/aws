export interface ProduceWorkOutputVO {
  name: string;
  workId: number;
  dataValue: number;
  unit: string;
}

export interface ProduceAnnualOutputVO {
  name: string;
  year: number;
  dataValue: number;
  unit: string;
}

export interface ProduceUpdateVO {
  id: number;
  name: string;
  status: number;
}

export interface ProduceVO {
  id: number;
  name: string;
  createTime: number;
  updateTime: number;
  status: number;
}

export interface ProduceAddVO {
  name: string;
}

export interface ProduceListVO {
  produceList: ProduceVO[];
  count: number;
}

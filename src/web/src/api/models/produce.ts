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

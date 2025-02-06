export interface ScaleUpdateVO {
  id: number;
  status: number;
}

export interface ScaleVO {
  id: number;
  skey: string;
  model: string;
  maxCapacity: number;
  minCapacity: number;
  unit: number;
  verificationInterval: number;
  displayInterval: number;
  unitDv: number;
  protocol: number;
  createTime: number;
  updateTime: number;
  status: number;
}

export interface ScaleAddVO {
  skey: string;
  model: string;
  maxCapacity: number;
  minCapacity: number;
  unit: number;
  verificationInterval: number;
  displayInterval: number;
  unitDv: number;
  protocol: number;
}

export interface ScaleListVO {
  scaleList: ScaleVO[];
  count: number;
}

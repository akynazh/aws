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
  verificationInterval: number; // 检定分度值
  displayInterval: number; // 显示分度值
  unitDv: number; // 分度值单位
  protocol: number; // 通信协议，0 为 MQTT，1 为 HTTP
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

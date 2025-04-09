export interface RecordAddVO {
  workId: number;
  employeeId: number;
  scaleId: number;
  dataValue: number;
  dataErrorMargin: number;
  unit: number;
  dataTime: number;
}

export interface RecordVO {
  id: number;
  workId: number;
  produceId: number;
  image: string;
  employeeId: number;
  scaleId: number;
  dataValue: number;
  dataErrorMargin: number;
  unit: number;
  dataTime: number;
}

export interface RecordListVO {
  recordList: RecordVO[];
  count: number;
}

export interface RecordsGetVO {
  page: number;
  size: number;
  workId: number;
  employeeId: number;
  scaleId: number;
}

import request from "@/utils/request";
import {
  type ProduceVO,
  type ProduceUpdateVO,
  type ProduceAddVO,
  type ProduceListVO,
  type ProduceAnnualOutputVO,
  type ProduceWorkOutputVO,
} from "@/models";

export const reqUpdateProduce = (data: ProduceUpdateVO) =>
  request.put<ProduceVO>("/produce", data);
export const reqAddProduce = (data: ProduceAddVO) =>
  request.post<ProduceVO>("/produce", data);
export const reqGetProduce = (id: number) =>
  request.get<ProduceVO>(`/produce/${id}`);
export const reqGetProduceByName = (name: string) =>
  request.get<ProduceVO>(`/produce?name=${name}`);
export const reqGetProduces = (page: number = 0, size: number = 10) =>
  request.get<ProduceListVO>("/produce/list", { params: { page, size } });
export const reqGetProduceAnnualOutput = (id: number) =>
  request.get<ProduceAnnualOutputVO[]>(`/produce/summary/year?id=${id}`);
export const reqGetProduceWorkOutput = (id: number) =>
  request.get<ProduceWorkOutputVO[]>(`/produce/summary/work?id=${id}`);

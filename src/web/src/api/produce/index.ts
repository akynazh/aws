import request from "@/utils/request";
import type { ProduceVO, ProduceUpdateVO, ProduceAddVO, ProduceListVO } from "@/models";

export const reqUpdateProduce = (data: ProduceUpdateVO) => request.put<ProduceVO>("/produce", data);
export const reqAddProduce = (data: ProduceAddVO) => request.post<ProduceVO>("/produce", data);
export const reqGetProduce = (id: number) => request.get<ProduceVO>(`/produce/${id}`);
export const reqGetProduces = (page: number = 0, size: number = 10) => request.get<ProduceListVO>("/produce/list", { params: { page, size } });

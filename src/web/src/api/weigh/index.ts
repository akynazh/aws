import request from "@/utils/request";
import type { ScaleVO, ScaleUpdateVO, ScaleAddVO, ScaleListVO, RecordAddVO, RecordVO, RecordListVO, RecordsGetVO } from "@/api/models";

export const reqUpdateScale = (data: ScaleUpdateVO) => request.put<ScaleVO>("/weigh/scale", data);
export const reqAddScale = (data: ScaleAddVO) => request.post<ScaleVO>("/weigh/scale", data);
export const reqGetScale = (id: number) => request.get<ScaleVO>(`/weigh/scale/${id}`);
export const reqGetScales = (page: number = 0, size: number = 10) => request.get<ScaleListVO>("/weigh/scale/list", { params: { page, size } });
export const reqAddRecord = (data: RecordAddVO) => request.post<RecordVO>("/weigh/record", data);
export const reqGetRecords = (data: RecordsGetVO) => request.get<RecordListVO>("/weigh/record/list", { params: data });

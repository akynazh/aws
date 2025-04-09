import request from "@/utils/request";
import type { ScaleVO, ScaleUpdateVO, ScaleAddVO, ScaleListVO, RecordAddVO, RecordVO, RecordListVO, RecordsGetVO, UserWorkOutputVO, TodoVO, TodosGetVO, TodoListVO } from "@/models";

export const reqUpdateScale = (data: ScaleUpdateVO) => request.put<ScaleVO>("/weigh/scale", data);
export const reqAddScale = (data: ScaleAddVO) => request.post<ScaleVO>("/weigh/scale", data);
export const reqGetScale = (id: number) => request.get<ScaleVO>(`/weigh/scale/${id}`);
export const reqGetScaleByKey = (key: string) => request.get<ScaleVO>(`/weigh/scale?key=${key}`);
export const reqGetScales = (page: number = 0, size: number = 10) => request.get<ScaleListVO>("/weigh/scale/list", { params: { page, size } });
export const reqAddRecord = (data: RecordAddVO) => request.post<RecordVO>("/weigh/record", data);
export const reqGetRecords = (data: RecordsGetVO) => request.post<RecordListVO>("/weigh/record/list", data);
export const reqGetUserWorkSummary = (id:number) => request.get<UserWorkOutputVO[]>(`/weigh/summary?id=${id}`);
export const reqGetTodos = (data: TodosGetVO) => request.post<TodoListVO>("/weigh/record/todo/list", data);
export const reqHandleTodoRecord = (data: TodoVO) => request.post<string>("/weigh/record/todo", data);
export const dropTodoRecord = (id: number) => request.delete<string>(`/weigh/record/todo?id=${id}`);
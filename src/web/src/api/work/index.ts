import request from "@/utils/request";
import type { WorkVO, WorkUpdateVO, WorkAddVO, WorkListVO, WorkReassignVO, WorkAssignmentsVO, WorkAssignVO, MyAssignmentsVO } from "@/models";

export const reqUpdateWork = (data: WorkUpdateVO) => request.put<WorkVO>("/work", data);
export const reqAddWork = (data: WorkAddVO) => request.post<WorkVO>("/work", data);
// export const reqReassignWork = (data: WorkReassignVO) => request.put<WorkAssignmentsVO>("/work/assign", data);
// export const reqAssignWork = (data: WorkAssignVO) => request.post<WorkAssignmentsVO>("/work/assign", data);
export const reqGetWork = (id: number) => request.get<WorkVO>(`/work/${id}`);
// export const reqGetWorkAssignments = (id: number) => request.get<WorkAssignmentsVO>(`/work/${id}/assignments`);
export const reqGetProduceWorks = (id: number, page: number = 0, size: number = 10) => request.get<WorkVO[]>(`/work/produce/${id}`, { params: { page, size } });
export const reqGetWorks = (page: number = 0, size: number = 10) => request.get<WorkListVO>("/work/list", { params: { page, size } });
// export const reqGetMyAssignments = () => request.get<MyAssignmentsVO>("/work/assignments/me");

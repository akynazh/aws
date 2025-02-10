export interface UserState {
    token: string | null;
    id: number;
    uid: string;
    name: string;
    roles: string;
    createTime: number;
    updateTime: number;
    status: number;
}

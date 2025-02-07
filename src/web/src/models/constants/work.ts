export enum WorkStatus {
    NOT_STARTED = 0,
    ONGOING = 1,
    FINISHED = 2,
    CANCELED = 3,
    DELETED = 4
}

export const WorkStatusMap = {
    [WorkStatus.NOT_STARTED]: '未开始',
    [WorkStatus.ONGOING]: '进行中',
    [WorkStatus.FINISHED]: '已结束',
    [WorkStatus.CANCELED]: '已取消',
    [WorkStatus.DELETED]: '已删除'
};

export enum AssignmentStatus {
    DISABLED = 0,
    ENABLE = 1,
    DELETED = 2
}

export const AssignmentStatusMap = {
    [AssignmentStatus.DISABLED]: '禁用',
    [AssignmentStatus.ENABLE]: '启用',
    [AssignmentStatus.DELETED]: '已删除'
};

export enum ScaleUnit {
    MG = 0,
    G = 1,
    KG = 2,
    T = 3,
    LB = 4,
    OZ = 5,
    CT = 6
}

export const ScaleUnitMap = {
    [ScaleUnit.MG]: 'mg',
    [ScaleUnit.G]: 'g',
    [ScaleUnit.KG]: 'kg',
    [ScaleUnit.T]: 't',
    [ScaleUnit.LB]: 'lb',
    [ScaleUnit.OZ]: 'oz',
    [ScaleUnit.CT]: 'ct'
};

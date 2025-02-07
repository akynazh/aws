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
    [ScaleUnit.MG]: "mg",
    [ScaleUnit.G]: "g",
    [ScaleUnit.KG]: "kg",
    [ScaleUnit.T]: "t",
    [ScaleUnit.LB]: "lb",
    [ScaleUnit.OZ]: "oz",
    [ScaleUnit.CT]: "ct"
};

export enum ScaleStatus {
    DISABLED = 0,
    ENABLE = 1,
    DELETED = 2
}

export const ScaleStatusMap = {
    [ScaleStatus.DISABLED]: "禁用",
    [ScaleStatus.ENABLE]: "启用",
    [ScaleStatus.DELETED]: "已删除"
};

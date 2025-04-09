export interface TodoVO {
    id: number
    produceId: number
    produceName: string
    image: string
    employeeId: number
    scaleId: number
    dataValue: number
    dataErrorMargin: number
    unit: number
    dataTime: number
}

export interface TodosGetVO {
    page: number
    size: number
}

export interface TodoListVO {
    todoVOList: TodoVO[]
    count: number
}

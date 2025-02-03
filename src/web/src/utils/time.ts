//封装一个函数：获取一个结果：当前早上|上午|下午|晚上
export const getTime = () => {
    let message = ''
    //使用内置构造函数Date()获取当前时间
    let hours = new Date().getHours()
    //判断当前时间
    if (hours >= 6 && hours < 9) {
        message = '早上'
    } else if (hours >= 9 && hours < 12) {
        message = '上午'
    } else if (hours >= 12 && hours < 18) {
        message = '下午'
    } else {
        message = '晚上'
    }
    return message
}

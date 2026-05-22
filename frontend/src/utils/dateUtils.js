import dayjs from 'dayjs'

export const formatToLocalDate = (date) => {
  return dayjs(date).format('YYYY-MM-DD')
}

export const formatToLocalDateTime = (date) => {
  return dayjs(date).format('YYYY-MM-DD HH:mm:ss')
}

// 处理日期范围
export const processDateRange = (dateRange) => {
  let rslt = [null, null]
  if (dateRange && dateRange.length === 1) rslt[0] = dayjs(dateRange[0]).startOf('day').format('YYYY-MM-DD HH:mm:ss')
  if (dateRange && dateRange.length === 2) {
    rslt[0] = dayjs(dateRange[0]).startOf('day').format('YYYY-MM-DD HH:mm:ss')
    rslt[1] = dayjs(dateRange[1]).endOf('day').format('YYYY-MM-DD HH:mm:ss')
  }
  return rslt
}

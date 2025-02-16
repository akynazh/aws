---
title: AWS
language_tabs:
  - shell: Shell
  - http: HTTP
  - javascript: JavaScript
  - ruby: Ruby
  - python: Python
  - php: PHP
  - java: Java
  - go: Go
toc_footers: []
includes: []
search: true
code_clipboard: true
highlight_theme: darkula
headingLevel: 2
generator: "@tarslib/widdershins v4.0.28"

---

# AWS

Base URLs:

# Authentication

# 工作服务模块

<a id="opIdupdateWork"></a>

## PUT 更新采摘工作

PUT /work

> Body Parameters

```json
{
  "id": 0,
  "produceId": 0,
  "startTime": 0,
  "endTime": 0,
  "dataValue": 0,
  "unit": 0,
  "status": 0
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|Authorization|header|string| no |none|
|body|body|[WorkUpdateVO](#schemaworkupdatevo)| no |none|

> Response Examples

> 200 Response

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[WorkVO](#schemaworkvo)|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Bad Request|Inline|
|403|[Forbidden](https://tools.ietf.org/html/rfc7231#section-6.5.3)|Forbidden|Inline|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|Not Found|Inline|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|Internal Server Error|Inline|

### Responses Data Schema

HTTP Status Code **200**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|» id|integer(int64)|false|none||none|
|» produceId|integer(int64)|false|none||none|
|» startTime|integer(int64)|false|none||none|
|» endTime|integer(int64)|false|none||none|
|» dataValue|number|false|none||none|
|» unit|integer(int32)|false|none||none|
|» createTime|integer(int64)|false|none||none|
|» updateTime|integer(int64)|false|none||none|
|» status|integer(int32)|false|none||none|

<a id="opIdaddWork"></a>

## POST 添加采摘工作

POST /work

> Body Parameters

```json
{
  "produceId": 0,
  "startTime": 0,
  "endTime": 0
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|Authorization|header|string| no |none|
|body|body|[WorkAddVO](#schemaworkaddvo)| no |none|

> Response Examples

> 200 Response

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[WorkVO](#schemaworkvo)|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Bad Request|Inline|
|403|[Forbidden](https://tools.ietf.org/html/rfc7231#section-6.5.3)|Forbidden|Inline|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|Not Found|Inline|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|Internal Server Error|Inline|

### Responses Data Schema

HTTP Status Code **200**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|» id|integer(int64)|false|none||none|
|» produceId|integer(int64)|false|none||none|
|» startTime|integer(int64)|false|none||none|
|» endTime|integer(int64)|false|none||none|
|» dataValue|number|false|none||none|
|» unit|integer(int32)|false|none||none|
|» createTime|integer(int64)|false|none||none|
|» updateTime|integer(int64)|false|none||none|
|» status|integer(int32)|false|none||none|

<a id="opIdreassignWork"></a>

## PUT 重新分配工作

PUT /work/assign

> Body Parameters

```json
{
  "workId": 0,
  "employeeIdList": [
    0
  ]
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|Authorization|header|string| no |none|
|body|body|[WorkReassignVO](#schemaworkreassignvo)| no |none|

> Response Examples

> 200 Response

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[WorkAssignmentsVO](#schemaworkassignmentsvo)|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Bad Request|Inline|
|403|[Forbidden](https://tools.ietf.org/html/rfc7231#section-6.5.3)|Forbidden|Inline|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|Not Found|Inline|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|Internal Server Error|Inline|

### Responses Data Schema

HTTP Status Code **200**

*作业分配信息*

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|» workId|integer(int64)|false|none||采摘作业编号|
|» employees|object|false|none||分配的员工列表|
|»» **additionalProperties**|string|false|none||none|

<a id="opIdassignWork"></a>

## POST 分配工作

POST /work/assign

> Body Parameters

```json
{
  "workId": 0,
  "employeeIdList": [
    0
  ]
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|Authorization|header|string| no |none|
|body|body|[WorkAssignVO](#schemaworkassignvo)| no |none|

> Response Examples

> 200 Response

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[WorkAssignmentsVO](#schemaworkassignmentsvo)|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Bad Request|Inline|
|403|[Forbidden](https://tools.ietf.org/html/rfc7231#section-6.5.3)|Forbidden|Inline|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|Not Found|Inline|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|Internal Server Error|Inline|

### Responses Data Schema

HTTP Status Code **200**

*作业分配信息*

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|» workId|integer(int64)|false|none||采摘作业编号|
|» employees|object|false|none||分配的员工列表|
|»» **additionalProperties**|string|false|none||none|

<a id="opIdgetWork"></a>

## GET 获取采摘工作

GET /work/{id}

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)| yes |none|
|Authorization|header|string| no |none|

> Response Examples

> 200 Response

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[WorkVO](#schemaworkvo)|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Bad Request|Inline|
|403|[Forbidden](https://tools.ietf.org/html/rfc7231#section-6.5.3)|Forbidden|Inline|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|Not Found|Inline|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|Internal Server Error|Inline|

### Responses Data Schema

HTTP Status Code **200**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|» id|integer(int64)|false|none||none|
|» produceId|integer(int64)|false|none||none|
|» startTime|integer(int64)|false|none||none|
|» endTime|integer(int64)|false|none||none|
|» dataValue|number|false|none||none|
|» unit|integer(int32)|false|none||none|
|» createTime|integer(int64)|false|none||none|
|» updateTime|integer(int64)|false|none||none|
|» status|integer(int32)|false|none||none|

<a id="opIdgetWorkAssignments"></a>

## GET 获取采摘作业的分配情况

GET /work/{id}/assignments

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)| yes |none|
|Authorization|header|string| no |none|

> Response Examples

> 200 Response

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[WorkAssignmentsVO](#schemaworkassignmentsvo)|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Bad Request|Inline|
|403|[Forbidden](https://tools.ietf.org/html/rfc7231#section-6.5.3)|Forbidden|Inline|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|Not Found|Inline|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|Internal Server Error|Inline|

### Responses Data Schema

HTTP Status Code **200**

*作业分配信息*

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|» workId|integer(int64)|false|none||采摘作业编号|
|» employees|object|false|none||分配的员工列表|
|»» **additionalProperties**|string|false|none||none|

<a id="opIdgetProduceWorks"></a>

## GET 获取果实的采摘工作列表

GET /work/produce/{id}

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)| yes |none|
|Authorization|header|string| no |none|

> Response Examples

> 200 Response

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Bad Request|Inline|
|403|[Forbidden](https://tools.ietf.org/html/rfc7231#section-6.5.3)|Forbidden|Inline|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|Not Found|Inline|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|Internal Server Error|Inline|

### Responses Data Schema

HTTP Status Code **200**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|*anonymous*|[[WorkVO](#schemaworkvo)]|false|none||none|
|» id|integer(int64)|false|none||none|
|» produceId|integer(int64)|false|none||none|
|» startTime|integer(int64)|false|none||none|
|» endTime|integer(int64)|false|none||none|
|» dataValue|number|false|none||none|
|» unit|integer(int32)|false|none||none|
|» createTime|integer(int64)|false|none||none|
|» updateTime|integer(int64)|false|none||none|
|» status|integer(int32)|false|none||none|

<a id="opIdgetWorks"></a>

## GET 获取采摘工作列表

GET /work/list

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|page|query|integer(int32)| no |none|
|size|query|integer(int32)| no |none|
|Authorization|header|string| no |none|

> Response Examples

> 200 Response

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[WorkListVO](#schemaworklistvo)|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Bad Request|Inline|
|403|[Forbidden](https://tools.ietf.org/html/rfc7231#section-6.5.3)|Forbidden|Inline|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|Not Found|Inline|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|Internal Server Error|Inline|

### Responses Data Schema

HTTP Status Code **200**

*作业列表*

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|» workList|[[WorkVO](#schemaworkvo)]|false|none||none|
|»» id|integer(int64)|false|none||none|
|»» produceId|integer(int64)|false|none||none|
|»» startTime|integer(int64)|false|none||none|
|»» endTime|integer(int64)|false|none||none|
|»» dataValue|number|false|none||none|
|»» unit|integer(int32)|false|none||none|
|»» createTime|integer(int64)|false|none||none|
|»» updateTime|integer(int64)|false|none||none|
|»» status|integer(int32)|false|none||none|
|» count|integer(int64)|false|none||none|

<a id="opIdgetMyAssignments"></a>

## GET 获取我被分配的工作

GET /work/assignments/me

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|Authorization|header|string| no |none|

> Response Examples

> 200 Response

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[MyAssignmentsVO](#schemamyassignmentsvo)|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Bad Request|Inline|
|403|[Forbidden](https://tools.ietf.org/html/rfc7231#section-6.5.3)|Forbidden|Inline|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|Not Found|Inline|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|Internal Server Error|Inline|

### Responses Data Schema

HTTP Status Code **200**

*员工被分配的作业*

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|» workIds|[integer]|false|none||采摘作业编号列表|

# 称重服务模块

<a id="opIdgetScaleByKey"></a>

## GET 根据 key 获取电子秤

GET /weigh/scale

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|key|query|string| yes |none|
|Authorization|header|string| no |none|

> Response Examples

> 200 Response

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[ScaleVO](#schemascalevo)|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Bad Request|Inline|
|403|[Forbidden](https://tools.ietf.org/html/rfc7231#section-6.5.3)|Forbidden|Inline|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|Not Found|Inline|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|Internal Server Error|Inline|

### Responses Data Schema

HTTP Status Code **200**

*电子秤信息*

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|» id|integer(int64)|false|none||电子秤编号|
|» skey|string|false|none||电子秤密钥|
|» model|string|false|none||型号|
|» maxCapacity|number|false|none||最大量程|
|» minCapacity|number|false|none||最小量程|
|» unit|integer(int32)|false|none||量程单位，0 为 mg，1 为 g，2 为 kg，3 为 t，4 为 lb（磅），5 为 oz（盎司），6 为 ct（克拉）|
|» verificationInterval|integer(int32)|false|none||检定分度值|
|» displayInterval|integer(int32)|false|none||显示分度值|
|» unitDv|integer(int32)|false|none||分度值单位，0 为 mg，1 为 g，2 为 kg，3 为 t，4 为 lb（磅），5 为 oz（盎司），6 为 ct（克拉）|
|» protocol|integer(int32)|false|none||通信协议，0 为 MQTT，1 为 HTTP|
|» createTime|integer(int64)|false|none||创建时间，毫秒级时间戳|
|» updateTime|integer(int64)|false|none||更新时间，毫秒级时间戳|
|» status|integer(int32)|false|none||状态，0 为禁用，1 为启用，2 为已删除|

<a id="opIdupdateScale"></a>

## PUT 更新电子秤信息

PUT /weigh/scale

> Body Parameters

```json
{
  "id": 0,
  "status": 0
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|Authorization|header|string| no |none|
|body|body|[ScaleUpdateVO](#schemascaleupdatevo)| no |none|

> Response Examples

> 200 Response

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[ScaleVO](#schemascalevo)|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Bad Request|Inline|
|403|[Forbidden](https://tools.ietf.org/html/rfc7231#section-6.5.3)|Forbidden|Inline|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|Not Found|Inline|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|Internal Server Error|Inline|

### Responses Data Schema

HTTP Status Code **200**

*电子秤信息*

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|» id|integer(int64)|false|none||电子秤编号|
|» skey|string|false|none||电子秤密钥|
|» model|string|false|none||型号|
|» maxCapacity|number|false|none||最大量程|
|» minCapacity|number|false|none||最小量程|
|» unit|integer(int32)|false|none||量程单位，0 为 mg，1 为 g，2 为 kg，3 为 t，4 为 lb（磅），5 为 oz（盎司），6 为 ct（克拉）|
|» verificationInterval|integer(int32)|false|none||检定分度值|
|» displayInterval|integer(int32)|false|none||显示分度值|
|» unitDv|integer(int32)|false|none||分度值单位，0 为 mg，1 为 g，2 为 kg，3 为 t，4 为 lb（磅），5 为 oz（盎司），6 为 ct（克拉）|
|» protocol|integer(int32)|false|none||通信协议，0 为 MQTT，1 为 HTTP|
|» createTime|integer(int64)|false|none||创建时间，毫秒级时间戳|
|» updateTime|integer(int64)|false|none||更新时间，毫秒级时间戳|
|» status|integer(int32)|false|none||状态，0 为禁用，1 为启用，2 为已删除|

<a id="opIdaddScale"></a>

## POST 添加电子秤

POST /weigh/scale

> Body Parameters

```json
{
  "skey": "string",
  "model": "string",
  "maxCapacity": 0,
  "minCapacity": 0,
  "unit": 0,
  "verificationInterval": 0,
  "displayInterval": 0,
  "unitDv": 0,
  "protocol": 0
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|Authorization|header|string| no |none|
|body|body|[ScaleAddVO](#schemascaleaddvo)| no |none|

> Response Examples

> 200 Response

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[ScaleVO](#schemascalevo)|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Bad Request|Inline|
|403|[Forbidden](https://tools.ietf.org/html/rfc7231#section-6.5.3)|Forbidden|Inline|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|Not Found|Inline|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|Internal Server Error|Inline|

### Responses Data Schema

HTTP Status Code **200**

*电子秤信息*

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|» id|integer(int64)|false|none||电子秤编号|
|» skey|string|false|none||电子秤密钥|
|» model|string|false|none||型号|
|» maxCapacity|number|false|none||最大量程|
|» minCapacity|number|false|none||最小量程|
|» unit|integer(int32)|false|none||量程单位，0 为 mg，1 为 g，2 为 kg，3 为 t，4 为 lb（磅），5 为 oz（盎司），6 为 ct（克拉）|
|» verificationInterval|integer(int32)|false|none||检定分度值|
|» displayInterval|integer(int32)|false|none||显示分度值|
|» unitDv|integer(int32)|false|none||分度值单位，0 为 mg，1 为 g，2 为 kg，3 为 t，4 为 lb（磅），5 为 oz（盎司），6 为 ct（克拉）|
|» protocol|integer(int32)|false|none||通信协议，0 为 MQTT，1 为 HTTP|
|» createTime|integer(int64)|false|none||创建时间，毫秒级时间戳|
|» updateTime|integer(int64)|false|none||更新时间，毫秒级时间戳|
|» status|integer(int32)|false|none||状态，0 为禁用，1 为启用，2 为已删除|

<a id="opIdaddRecord"></a>

## POST 添加称重记录

POST /weigh/record

> Body Parameters

```json
{
  "workId": 0,
  "employeeId": 0,
  "scaleId": 0,
  "dataValue": 0,
  "dataErrorMargin": 0,
  "unit": 0,
  "dataTime": 0
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|Authorization|header|string| no |none|
|body|body|[RecordAddVO](#schemarecordaddvo)| no |none|

> Response Examples

> 200 Response

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[RecordVO](#schemarecordvo)|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Bad Request|Inline|
|403|[Forbidden](https://tools.ietf.org/html/rfc7231#section-6.5.3)|Forbidden|Inline|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|Not Found|Inline|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|Internal Server Error|Inline|

### Responses Data Schema

HTTP Status Code **200**

*称重记录*

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|» id|integer(int64)|false|none||称重记录编号|
|» workId|integer(int64)|false|none||采摘作业编号|
|» employeeId|integer(int64)|false|none||员工编号|
|» scaleId|integer(int64)|false|none||电子秤编号|
|» dataValue|number|false|none||称重结果|
|» dataErrorMargin|number|false|none||称量结果误差范围，正负多少|
|» unit|integer(int32)|false|none||称重单位，0 为 mg，1 为 g，2 为 kg，3 为 t，4 为 lb（磅），5 为 oz（盎司），6 为 ct（克拉）|
|» dataTime|integer(int64)|false|none||称重时间，毫秒级时间戳|

<a id="opIdgetRecords"></a>

## POST 获取称重记录

POST /weigh/record/list

> Body Parameters

```json
{
  "page": 0,
  "size": 0,
  "workId": 0,
  "employeeId": 0,
  "scaleId": 0
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|Authorization|header|string| no |none|
|body|body|[RecordsGetVO](#schemarecordsgetvo)| no |none|

> Response Examples

> 200 Response

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[RecordListVO](#schemarecordlistvo)|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Bad Request|Inline|
|403|[Forbidden](https://tools.ietf.org/html/rfc7231#section-6.5.3)|Forbidden|Inline|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|Not Found|Inline|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|Internal Server Error|Inline|

### Responses Data Schema

HTTP Status Code **200**

*称重记录列表*

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|» recordList|[[RecordVO](#schemarecordvo)]|false|none||[称重记录]|
|»» id|integer(int64)|false|none||称重记录编号|
|»» workId|integer(int64)|false|none||采摘作业编号|
|»» employeeId|integer(int64)|false|none||员工编号|
|»» scaleId|integer(int64)|false|none||电子秤编号|
|»» dataValue|number|false|none||称重结果|
|»» dataErrorMargin|number|false|none||称量结果误差范围，正负多少|
|»» unit|integer(int32)|false|none||称重单位，0 为 mg，1 为 g，2 为 kg，3 为 t，4 为 lb（磅），5 为 oz（盎司），6 为 ct（克拉）|
|»» dataTime|integer(int64)|false|none||称重时间，毫秒级时间戳|
|» count|integer(int64)|false|none||none|

<a id="opIdgetUserWorkSummary"></a>

## GET 获取员工各作业采摘量

GET /weigh/summary

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|id|query|integer(int64)| yes |none|
|Authorization|header|string| no |none|

> Response Examples

> 200 Response

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Bad Request|Inline|
|403|[Forbidden](https://tools.ietf.org/html/rfc7231#section-6.5.3)|Forbidden|Inline|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|Not Found|Inline|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|Internal Server Error|Inline|

### Responses Data Schema

HTTP Status Code **200**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|*anonymous*|[[UserWorkOutputVO](#schemauserworkoutputvo)]|false|none||none|
|» name|string|false|none||none|
|» workId|integer(int64)|false|none||none|
|» produceName|string|false|none||none|
|» dataValue|number|false|none||none|
|» unit|string|false|none||none|

<a id="opIdgetScale"></a>

## GET 获取电子秤

GET /weigh/scale/{id}

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)| yes |none|
|Authorization|header|string| no |none|

> Response Examples

> 200 Response

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[ScaleVO](#schemascalevo)|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Bad Request|Inline|
|403|[Forbidden](https://tools.ietf.org/html/rfc7231#section-6.5.3)|Forbidden|Inline|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|Not Found|Inline|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|Internal Server Error|Inline|

### Responses Data Schema

HTTP Status Code **200**

*电子秤信息*

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|» id|integer(int64)|false|none||电子秤编号|
|» skey|string|false|none||电子秤密钥|
|» model|string|false|none||型号|
|» maxCapacity|number|false|none||最大量程|
|» minCapacity|number|false|none||最小量程|
|» unit|integer(int32)|false|none||量程单位，0 为 mg，1 为 g，2 为 kg，3 为 t，4 为 lb（磅），5 为 oz（盎司），6 为 ct（克拉）|
|» verificationInterval|integer(int32)|false|none||检定分度值|
|» displayInterval|integer(int32)|false|none||显示分度值|
|» unitDv|integer(int32)|false|none||分度值单位，0 为 mg，1 为 g，2 为 kg，3 为 t，4 为 lb（磅），5 为 oz（盎司），6 为 ct（克拉）|
|» protocol|integer(int32)|false|none||通信协议，0 为 MQTT，1 为 HTTP|
|» createTime|integer(int64)|false|none||创建时间，毫秒级时间戳|
|» updateTime|integer(int64)|false|none||更新时间，毫秒级时间戳|
|» status|integer(int32)|false|none||状态，0 为禁用，1 为启用，2 为已删除|

<a id="opIdgetScales"></a>

## GET 获取电子秤列表

GET /weigh/scale/list

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|page|query|integer(int32)| no |none|
|size|query|integer(int32)| no |none|
|Authorization|header|string| no |none|

> Response Examples

> 200 Response

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[ScaleListVO](#schemascalelistvo)|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Bad Request|Inline|
|403|[Forbidden](https://tools.ietf.org/html/rfc7231#section-6.5.3)|Forbidden|Inline|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|Not Found|Inline|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|Internal Server Error|Inline|

### Responses Data Schema

HTTP Status Code **200**

*电子秤列表*

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|» scaleList|[[ScaleVO](#schemascalevo)]|false|none||[电子秤信息]|
|»» id|integer(int64)|false|none||电子秤编号|
|»» skey|string|false|none||电子秤密钥|
|»» model|string|false|none||型号|
|»» maxCapacity|number|false|none||最大量程|
|»» minCapacity|number|false|none||最小量程|
|»» unit|integer(int32)|false|none||量程单位，0 为 mg，1 为 g，2 为 kg，3 为 t，4 为 lb（磅），5 为 oz（盎司），6 为 ct（克拉）|
|»» verificationInterval|integer(int32)|false|none||检定分度值|
|»» displayInterval|integer(int32)|false|none||显示分度值|
|»» unitDv|integer(int32)|false|none||分度值单位，0 为 mg，1 为 g，2 为 kg，3 为 t，4 为 lb（磅），5 为 oz（盎司），6 为 ct（克拉）|
|»» protocol|integer(int32)|false|none||通信协议，0 为 MQTT，1 为 HTTP|
|»» createTime|integer(int64)|false|none||创建时间，毫秒级时间戳|
|»» updateTime|integer(int64)|false|none||更新时间，毫秒级时间戳|
|»» status|integer(int32)|false|none||状态，0 为禁用，1 为启用，2 为已删除|
|» count|integer(int64)|false|none||none|

# 用户管理模块

<a id="opIdgetMe"></a>

## GET 用户获取个人信息

GET /user

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|Authorization|header|string| no |none|

> Response Examples

> 200 Response

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[UserVO](#schemauservo)|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Bad Request|Inline|
|403|[Forbidden](https://tools.ietf.org/html/rfc7231#section-6.5.3)|Forbidden|Inline|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|Not Found|Inline|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|Internal Server Error|Inline|

### Responses Data Schema

HTTP Status Code **200**

*用户信息*

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|» id|integer(int64)|false|none||用户编号|
|» uid|string|false|none||用户身份证|
|» name|string|false|none||员工姓名|
|» roles|string|false|none||角色，以英文逗号分隔，比如 ROLE_EMPLOYEE,ROLE_ADMIN|
|» createTime|integer(int64)|false|none||创建时间，毫秒级时间戳|
|» updateTime|integer(int64)|false|none||更新时间，毫秒级时间戳|
|» status|integer(int32)|false|none||状态，0 为禁用，1 为启用，2 为已删除|

<a id="opIdupdateUser"></a>

## PUT 管理员更新用户

PUT /user

> Body Parameters

```json
{
  "uid": "string",
  "name": "string",
  "password": "string",
  "status": 0,
  "roles": "string"
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|Authorization|header|string| no |none|
|body|body|[UserUpdateVO](#schemauserupdatevo)| no |none|

> Response Examples

> 200 Response

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[UserVO](#schemauservo)|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Bad Request|Inline|
|403|[Forbidden](https://tools.ietf.org/html/rfc7231#section-6.5.3)|Forbidden|Inline|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|Not Found|Inline|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|Internal Server Error|Inline|

### Responses Data Schema

HTTP Status Code **200**

*用户信息*

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|» id|integer(int64)|false|none||用户编号|
|» uid|string|false|none||用户身份证|
|» name|string|false|none||员工姓名|
|» roles|string|false|none||角色，以英文逗号分隔，比如 ROLE_EMPLOYEE,ROLE_ADMIN|
|» createTime|integer(int64)|false|none||创建时间，毫秒级时间戳|
|» updateTime|integer(int64)|false|none||更新时间，毫秒级时间戳|
|» status|integer(int32)|false|none||状态，0 为禁用，1 为启用，2 为已删除|

<a id="opIdaddUser"></a>

## POST 管理员添加用户

POST /user

> Body Parameters

```json
{
  "uid": "string",
  "name": "string",
  "roles": "string"
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|Authorization|header|string| no |none|
|body|body|[UserRegisterVO](#schemauserregistervo)| no |none|

> Response Examples

> 200 Response

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[UserVO](#schemauservo)|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Bad Request|Inline|
|403|[Forbidden](https://tools.ietf.org/html/rfc7231#section-6.5.3)|Forbidden|Inline|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|Not Found|Inline|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|Internal Server Error|Inline|

### Responses Data Schema

HTTP Status Code **200**

*用户信息*

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|» id|integer(int64)|false|none||用户编号|
|» uid|string|false|none||用户身份证|
|» name|string|false|none||员工姓名|
|» roles|string|false|none||角色，以英文逗号分隔，比如 ROLE_EMPLOYEE,ROLE_ADMIN|
|» createTime|integer(int64)|false|none||创建时间，毫秒级时间戳|
|» updateTime|integer(int64)|false|none||更新时间，毫秒级时间戳|
|» status|integer(int32)|false|none||状态，0 为禁用，1 为启用，2 为已删除|

<a id="opIdupdateMe"></a>

## PUT 用户更新个人信息

PUT /user/me

> Body Parameters

```json
{
  "name": "string",
  "password": "string"
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|Authorization|header|string| no |none|
|body|body|[UserUpdateMeVO](#schemauserupdatemevo)| no |none|

> Response Examples

> 200 Response

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[UserVO](#schemauservo)|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Bad Request|Inline|
|403|[Forbidden](https://tools.ietf.org/html/rfc7231#section-6.5.3)|Forbidden|Inline|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|Not Found|Inline|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|Internal Server Error|Inline|

### Responses Data Schema

HTTP Status Code **200**

*用户信息*

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|» id|integer(int64)|false|none||用户编号|
|» uid|string|false|none||用户身份证|
|» name|string|false|none||员工姓名|
|» roles|string|false|none||角色，以英文逗号分隔，比如 ROLE_EMPLOYEE,ROLE_ADMIN|
|» createTime|integer(int64)|false|none||创建时间，毫秒级时间戳|
|» updateTime|integer(int64)|false|none||更新时间，毫秒级时间戳|
|» status|integer(int32)|false|none||状态，0 为禁用，1 为启用，2 为已删除|

<a id="opIdlogin"></a>

## POST 用户登录

POST /user/login

> Body Parameters

```json
{
  "uid": "string",
  "password": "string"
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|Authorization|header|string| no |none|
|body|body|[UserLoginVO](#schemauserloginvo)| no |none|

> Response Examples

> 200 Response

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|string|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Bad Request|Inline|
|403|[Forbidden](https://tools.ietf.org/html/rfc7231#section-6.5.3)|Forbidden|Inline|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|Not Found|Inline|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|Internal Server Error|Inline|

### Responses Data Schema

<a id="opIdgetEmployee"></a>

## GET 管理员获取用户信息

GET /user/{uid}

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|uid|path|string| yes |none|
|Authorization|header|string| no |none|

> Response Examples

> 200 Response

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[UserVO](#schemauservo)|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Bad Request|Inline|
|403|[Forbidden](https://tools.ietf.org/html/rfc7231#section-6.5.3)|Forbidden|Inline|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|Not Found|Inline|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|Internal Server Error|Inline|

### Responses Data Schema

HTTP Status Code **200**

*用户信息*

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|» id|integer(int64)|false|none||用户编号|
|» uid|string|false|none||用户身份证|
|» name|string|false|none||员工姓名|
|» roles|string|false|none||角色，以英文逗号分隔，比如 ROLE_EMPLOYEE,ROLE_ADMIN|
|» createTime|integer(int64)|false|none||创建时间，毫秒级时间戳|
|» updateTime|integer(int64)|false|none||更新时间，毫秒级时间戳|
|» status|integer(int32)|false|none||状态，0 为禁用，1 为启用，2 为已删除|

<a id="opIdgetUsers"></a>

## GET 管理员获取用户列表

GET /user/list

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|page|query|integer(int32)| no |none|
|size|query|integer(int32)| no |none|
|Authorization|header|string| no |none|

> Response Examples

> 200 Response

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[UserListVO](#schemauserlistvo)|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Bad Request|Inline|
|403|[Forbidden](https://tools.ietf.org/html/rfc7231#section-6.5.3)|Forbidden|Inline|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|Not Found|Inline|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|Internal Server Error|Inline|

### Responses Data Schema

HTTP Status Code **200**

*用户列表*

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|» userList|[[UserVO](#schemauservo)]|false|none||[用户信息]|
|»» id|integer(int64)|false|none||用户编号|
|»» uid|string|false|none||用户身份证|
|»» name|string|false|none||员工姓名|
|»» roles|string|false|none||角色，以英文逗号分隔，比如 ROLE_EMPLOYEE,ROLE_ADMIN|
|»» createTime|integer(int64)|false|none||创建时间，毫秒级时间戳|
|»» updateTime|integer(int64)|false|none||更新时间，毫秒级时间戳|
|»» status|integer(int32)|false|none||状态，0 为禁用，1 为启用，2 为已删除|
|» count|integer(int64)|false|none||none|

# 公共模块

<a id="opIdindex"></a>

## GET 信息

GET /common/info

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|Authorization|header|string| no |none|

> Response Examples

> 200 Response

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|string|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Bad Request|Inline|
|403|[Forbidden](https://tools.ietf.org/html/rfc7231#section-6.5.3)|Forbidden|Inline|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|Not Found|Inline|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|Internal Server Error|Inline|

### Responses Data Schema

# 果实模块

<a id="opIdgetProduceByName"></a>

## GET 根据名称获取果实

GET /produce

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|name|query|string| yes |none|
|Authorization|header|string| no |none|

> Response Examples

> 200 Response

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[ProduceVO](#schemaproducevo)|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Bad Request|Inline|
|403|[Forbidden](https://tools.ietf.org/html/rfc7231#section-6.5.3)|Forbidden|Inline|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|Not Found|Inline|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|Internal Server Error|Inline|

### Responses Data Schema

HTTP Status Code **200**

*农产品*

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|» id|integer(int64)|false|none||农产品编号|
|» name|string|false|none||农产品名称|
|» createTime|integer(int64)|false|none||创建时间|
|» updateTime|integer(int64)|false|none||更新时间|
|» status|integer(int32)|false|none||状态，0 为未种植，1 为在种植，2 为已删除|

<a id="opIdupdateProduce"></a>

## PUT 更新果实

PUT /produce

> Body Parameters

```json
{
  "id": 0,
  "name": "string",
  "status": 0
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|Authorization|header|string| no |none|
|body|body|[ProduceUpdateVO](#schemaproduceupdatevo)| no |none|

> Response Examples

> 200 Response

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[ProduceVO](#schemaproducevo)|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Bad Request|Inline|
|403|[Forbidden](https://tools.ietf.org/html/rfc7231#section-6.5.3)|Forbidden|Inline|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|Not Found|Inline|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|Internal Server Error|Inline|

### Responses Data Schema

HTTP Status Code **200**

*农产品*

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|» id|integer(int64)|false|none||农产品编号|
|» name|string|false|none||农产品名称|
|» createTime|integer(int64)|false|none||创建时间|
|» updateTime|integer(int64)|false|none||更新时间|
|» status|integer(int32)|false|none||状态，0 为未种植，1 为在种植，2 为已删除|

<a id="opIdaddProduce"></a>

## POST 添加果实

POST /produce

> Body Parameters

```json
{
  "name": "string"
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|Authorization|header|string| no |none|
|body|body|[ProduceAddVO](#schemaproduceaddvo)| no |none|

> Response Examples

> 200 Response

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[ProduceVO](#schemaproducevo)|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Bad Request|Inline|
|403|[Forbidden](https://tools.ietf.org/html/rfc7231#section-6.5.3)|Forbidden|Inline|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|Not Found|Inline|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|Internal Server Error|Inline|

### Responses Data Schema

HTTP Status Code **200**

*农产品*

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|» id|integer(int64)|false|none||农产品编号|
|» name|string|false|none||农产品名称|
|» createTime|integer(int64)|false|none||创建时间|
|» updateTime|integer(int64)|false|none||更新时间|
|» status|integer(int32)|false|none||状态，0 为未种植，1 为在种植，2 为已删除|

<a id="opIdgetProduce"></a>

## GET 获取果实

GET /produce/{id}

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|id|path|integer(int64)| yes |none|
|Authorization|header|string| no |none|

> Response Examples

> 200 Response

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[ProduceVO](#schemaproducevo)|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Bad Request|Inline|
|403|[Forbidden](https://tools.ietf.org/html/rfc7231#section-6.5.3)|Forbidden|Inline|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|Not Found|Inline|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|Internal Server Error|Inline|

### Responses Data Schema

HTTP Status Code **200**

*农产品*

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|» id|integer(int64)|false|none||农产品编号|
|» name|string|false|none||农产品名称|
|» createTime|integer(int64)|false|none||创建时间|
|» updateTime|integer(int64)|false|none||更新时间|
|» status|integer(int32)|false|none||状态，0 为未种植，1 为在种植，2 为已删除|

<a id="opIdgetProduceAnnualOutput"></a>

## GET 获取果实年产量

GET /produce/summary/year

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|id|query|integer(int64)| yes |none|
|Authorization|header|string| no |none|

> Response Examples

> 200 Response

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Bad Request|Inline|
|403|[Forbidden](https://tools.ietf.org/html/rfc7231#section-6.5.3)|Forbidden|Inline|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|Not Found|Inline|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|Internal Server Error|Inline|

### Responses Data Schema

HTTP Status Code **200**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|*anonymous*|[[ProduceAnnualOutputVO](#schemaproduceannualoutputvo)]|false|none||none|
|» name|string|false|none||none|
|» year|integer(int32)|false|none||none|
|» dataValue|number|false|none||none|
|» unit|string|false|none||none|

<a id="opIdgetProduceWorkOutput"></a>

## GET 获取果实分批产量

GET /produce/summary/work

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|id|query|integer(int64)| yes |none|
|Authorization|header|string| no |none|

> Response Examples

> 200 Response

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|Inline|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Bad Request|Inline|
|403|[Forbidden](https://tools.ietf.org/html/rfc7231#section-6.5.3)|Forbidden|Inline|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|Not Found|Inline|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|Internal Server Error|Inline|

### Responses Data Schema

HTTP Status Code **200**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|*anonymous*|[[ProduceWorkOutputVO](#schemaproduceworkoutputvo)]|false|none||none|
|» name|string|false|none||none|
|» workId|integer(int64)|false|none||none|
|» dataValue|number|false|none||none|
|» unit|string|false|none||none|

<a id="opIdgetProduces"></a>

## GET 获取果实列表

GET /produce/list

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|page|query|integer(int32)| no |none|
|size|query|integer(int32)| no |none|
|Authorization|header|string| no |none|

> Response Examples

> 200 Response

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|OK|[ProduceListVO](#schemaproducelistvo)|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Bad Request|Inline|
|403|[Forbidden](https://tools.ietf.org/html/rfc7231#section-6.5.3)|Forbidden|Inline|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|Not Found|Inline|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|Internal Server Error|Inline|

### Responses Data Schema

HTTP Status Code **200**

*农产品列表*

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|» produceList|[[ProduceVO](#schemaproducevo)]|false|none||[农产品]|
|»» id|integer(int64)|false|none||农产品编号|
|»» name|string|false|none||农产品名称|
|»» createTime|integer(int64)|false|none||创建时间|
|»» updateTime|integer(int64)|false|none||更新时间|
|»» status|integer(int32)|false|none||状态，0 为未种植，1 为在种植，2 为已删除|
|» count|integer(int64)|false|none||none|

# Data Schema

<h2 id="tocS_WorkUpdateVO">WorkUpdateVO</h2>

<a id="schemaworkupdatevo"></a>
<a id="schema_WorkUpdateVO"></a>
<a id="tocSworkupdatevo"></a>
<a id="tocsworkupdatevo"></a>

```json
{
  "id": 0,
  "produceId": 0,
  "startTime": 0,
  "endTime": 0,
  "dataValue": 0,
  "unit": 0,
  "status": 0
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|id|integer(int64)|false|none||none|
|produceId|integer(int64)|false|none||none|
|startTime|integer(int64)|false|none||none|
|endTime|integer(int64)|false|none||none|
|dataValue|number|false|none||none|
|unit|integer(int32)|false|none||none|
|status|integer(int32)|false|none||none|

<h2 id="tocS_WorkReassignVO">WorkReassignVO</h2>

<a id="schemaworkreassignvo"></a>
<a id="schema_WorkReassignVO"></a>
<a id="tocSworkreassignvo"></a>
<a id="tocsworkreassignvo"></a>

```json
{
  "workId": 0,
  "employeeIdList": [
    0
  ]
}

```

作业重新分配表单

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|workId|integer(int64)|false|none||采摘作业编号|
|employeeIdList|[integer]|false|none||员工编号列表|

<h2 id="tocS_ScaleUpdateVO">ScaleUpdateVO</h2>

<a id="schemascaleupdatevo"></a>
<a id="schema_ScaleUpdateVO"></a>
<a id="tocSscaleupdatevo"></a>
<a id="tocsscaleupdatevo"></a>

```json
{
  "id": 0,
  "status": 0
}

```

更新电子秤信息表单

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|id|integer(int64)|false|none||电子秤编号|
|status|integer(int32)|false|none||状态，0 为禁用，1 为启用，2 为已删除|

<h2 id="tocS_ScaleVO">ScaleVO</h2>

<a id="schemascalevo"></a>
<a id="schema_ScaleVO"></a>
<a id="tocSscalevo"></a>
<a id="tocsscalevo"></a>

```json
{
  "id": 0,
  "skey": "string",
  "model": "string",
  "maxCapacity": 0,
  "minCapacity": 0,
  "unit": 0,
  "verificationInterval": 0,
  "displayInterval": 0,
  "unitDv": 0,
  "protocol": 0,
  "createTime": 0,
  "updateTime": 0,
  "status": 0
}

```

电子秤信息

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|id|integer(int64)|false|none||电子秤编号|
|skey|string|false|none||电子秤密钥|
|model|string|false|none||型号|
|maxCapacity|number|false|none||最大量程|
|minCapacity|number|false|none||最小量程|
|unit|integer(int32)|false|none||量程单位，0 为 mg，1 为 g，2 为 kg，3 为 t，4 为 lb（磅），5 为 oz（盎司），6 为 ct（克拉）|
|verificationInterval|integer(int32)|false|none||检定分度值|
|displayInterval|integer(int32)|false|none||显示分度值|
|unitDv|integer(int32)|false|none||分度值单位，0 为 mg，1 为 g，2 为 kg，3 为 t，4 为 lb（磅），5 为 oz（盎司），6 为 ct（克拉）|
|protocol|integer(int32)|false|none||通信协议，0 为 MQTT，1 为 HTTP|
|createTime|integer(int64)|false|none||创建时间，毫秒级时间戳|
|updateTime|integer(int64)|false|none||更新时间，毫秒级时间戳|
|status|integer(int32)|false|none||状态，0 为禁用，1 为启用，2 为已删除|

<h2 id="tocS_UserUpdateVO">UserUpdateVO</h2>

<a id="schemauserupdatevo"></a>
<a id="schema_UserUpdateVO"></a>
<a id="tocSuserupdatevo"></a>
<a id="tocsuserupdatevo"></a>

```json
{
  "uid": "string",
  "name": "string",
  "password": "string",
  "status": 0,
  "roles": "string"
}

```

更新用户信息表单

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|uid|string|false|none||用户编号|
|name|string|false|none||用户姓名|
|password|string|false|none||密码|
|status|integer(int32)|false|none||状态，0 为禁用，1 为启用，2 为已删除|
|roles|string|false|none||角色|

<h2 id="tocS_UserUpdateMeVO">UserUpdateMeVO</h2>

<a id="schemauserupdatemevo"></a>
<a id="schema_UserUpdateMeVO"></a>
<a id="tocSuserupdatemevo"></a>
<a id="tocsuserupdatemevo"></a>

```json
{
  "name": "string",
  "password": "string"
}

```

更新个人信息表单

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|name|string|false|none||用户姓名|
|password|string|false|none||密码|

<h2 id="tocS_ProduceUpdateVO">ProduceUpdateVO</h2>

<a id="schemaproduceupdatevo"></a>
<a id="schema_ProduceUpdateVO"></a>
<a id="tocSproduceupdatevo"></a>
<a id="tocsproduceupdatevo"></a>

```json
{
  "id": 0,
  "name": "string",
  "status": 0
}

```

修改农产品信息表单

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|id|integer(int64)|false|none||农产品编号|
|name|string|false|none||农产品名称|
|status|integer(int32)|false|none||状态，0 为未种植，1 为在种植，2 为已删除|

<h2 id="tocS_ProduceVO">ProduceVO</h2>

<a id="schemaproducevo"></a>
<a id="schema_ProduceVO"></a>
<a id="tocSproducevo"></a>
<a id="tocsproducevo"></a>

```json
{
  "id": 0,
  "name": "string",
  "createTime": 0,
  "updateTime": 0,
  "status": 0
}

```

农产品

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|id|integer(int64)|false|none||农产品编号|
|name|string|false|none||农产品名称|
|createTime|integer(int64)|false|none||创建时间|
|updateTime|integer(int64)|false|none||更新时间|
|status|integer(int32)|false|none||状态，0 为未种植，1 为在种植，2 为已删除|

<h2 id="tocS_WorkAddVO">WorkAddVO</h2>

<a id="schemaworkaddvo"></a>
<a id="schema_WorkAddVO"></a>
<a id="tocSworkaddvo"></a>
<a id="tocsworkaddvo"></a>

```json
{
  "produceId": 0,
  "startTime": 0,
  "endTime": 0
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|produceId|integer(int64)|false|none||none|
|startTime|integer(int64)|false|none||none|
|endTime|integer(int64)|false|none||none|

<h2 id="tocS_WorkAssignVO">WorkAssignVO</h2>

<a id="schemaworkassignvo"></a>
<a id="schema_WorkAssignVO"></a>
<a id="tocSworkassignvo"></a>
<a id="tocsworkassignvo"></a>

```json
{
  "workId": 0,
  "employeeIdList": [
    0
  ]
}

```

作业分配表单

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|workId|integer(int64)|false|none||采摘作业编号|
|employeeIdList|[integer]|false|none||员工编号列表|

<h2 id="tocS_ScaleAddVO">ScaleAddVO</h2>

<a id="schemascaleaddvo"></a>
<a id="schema_ScaleAddVO"></a>
<a id="tocSscaleaddvo"></a>
<a id="tocsscaleaddvo"></a>

```json
{
  "skey": "string",
  "model": "string",
  "maxCapacity": 0,
  "minCapacity": 0,
  "unit": 0,
  "verificationInterval": 0,
  "displayInterval": 0,
  "unitDv": 0,
  "protocol": 0
}

```

新增电子秤信息表单

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|skey|string|false|none||电子秤密钥|
|model|string|false|none||型号|
|maxCapacity|number|false|none||最大量程|
|minCapacity|number|false|none||最小量程|
|unit|integer(int32)|false|none||量程单位，0 为 mg，1 为 g，2 为 kg，3 为 t，4 为 lb（磅），5 为 oz（盎司），6 为 ct（克拉）|
|verificationInterval|integer(int32)|false|none||检定分度值|
|displayInterval|integer(int32)|false|none||显示分度值|
|unitDv|integer(int32)|false|none||分度值单位，0 为 mg，1 为 g，2 为 kg，3 为 t，4 为 lb（磅），5 为 oz（盎司），6 为 ct（克拉）|
|protocol|integer(int32)|false|none||通信协议，0 为 MQTT，1 为 HTTP|

<h2 id="tocS_RecordAddVO">RecordAddVO</h2>

<a id="schemarecordaddvo"></a>
<a id="schema_RecordAddVO"></a>
<a id="tocSrecordaddvo"></a>
<a id="tocsrecordaddvo"></a>

```json
{
  "workId": 0,
  "employeeId": 0,
  "scaleId": 0,
  "dataValue": 0,
  "dataErrorMargin": 0,
  "unit": 0,
  "dataTime": 0
}

```

新增称重记录表单

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|workId|integer(int64)|false|none||采摘作业编号|
|employeeId|integer(int64)|false|none||员工编号|
|scaleId|integer(int64)|false|none||电子秤编号|
|dataValue|number|false|none||称重结果|
|dataErrorMargin|number|false|none||称量结果误差范围，正负多少|
|unit|integer(int32)|false|none||称重单位，0 为 mg，1 为 g，2 为 kg，3 为 t，4 为 lb（磅），5 为 oz（盎司），6 为 ct（克拉|
|dataTime|integer(int64)|false|none||称重时间，毫秒级时间戳|

<h2 id="tocS_RecordVO">RecordVO</h2>

<a id="schemarecordvo"></a>
<a id="schema_RecordVO"></a>
<a id="tocSrecordvo"></a>
<a id="tocsrecordvo"></a>

```json
{
  "id": 0,
  "workId": 0,
  "employeeId": 0,
  "scaleId": 0,
  "dataValue": 0,
  "dataErrorMargin": 0,
  "unit": 0,
  "dataTime": 0
}

```

称重记录

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|id|integer(int64)|false|none||称重记录编号|
|workId|integer(int64)|false|none||采摘作业编号|
|employeeId|integer(int64)|false|none||员工编号|
|scaleId|integer(int64)|false|none||电子秤编号|
|dataValue|number|false|none||称重结果|
|dataErrorMargin|number|false|none||称量结果误差范围，正负多少|
|unit|integer(int32)|false|none||称重单位，0 为 mg，1 为 g，2 为 kg，3 为 t，4 为 lb（磅），5 为 oz（盎司），6 为 ct（克拉）|
|dataTime|integer(int64)|false|none||称重时间，毫秒级时间戳|

<h2 id="tocS_UserRegisterVO">UserRegisterVO</h2>

<a id="schemauserregistervo"></a>
<a id="schema_UserRegisterVO"></a>
<a id="tocSuserregistervo"></a>
<a id="tocsuserregistervo"></a>

```json
{
  "uid": "string",
  "name": "string",
  "roles": "string"
}

```

用户注册表单

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|uid|string|false|none||用户编号|
|name|string|false|none||员工姓名|
|roles|string|false|none||角色，以英文逗号分隔，比如 ROLE_EMPLOYEE,ROLE_ADMIN|

<h2 id="tocS_UserLoginVO">UserLoginVO</h2>

<a id="schemauserloginvo"></a>
<a id="schema_UserLoginVO"></a>
<a id="tocSuserloginvo"></a>
<a id="tocsuserloginvo"></a>

```json
{
  "uid": "string",
  "password": "string"
}

```

用户登录表单

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|uid|string|false|none||用户编号|
|password|string|false|none||密码|

<h2 id="tocS_ProduceAddVO">ProduceAddVO</h2>

<a id="schemaproduceaddvo"></a>
<a id="schema_ProduceAddVO"></a>
<a id="tocSproduceaddvo"></a>
<a id="tocsproduceaddvo"></a>

```json
{
  "name": "string"
}

```

新增农产品表单

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|name|string|false|none||农产品名称|

<h2 id="tocS_RecordsGetVO">RecordsGetVO</h2>

<a id="schemarecordsgetvo"></a>
<a id="schema_RecordsGetVO"></a>
<a id="tocSrecordsgetvo"></a>
<a id="tocsrecordsgetvo"></a>

```json
{
  "page": 0,
  "size": 0,
  "workId": 0,
  "employeeId": 0,
  "scaleId": 0
}

```

称重记录查询参数

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|page|integer(int32)|false|none||当前页码|
|size|integer(int32)|false|none||每页记录数|
|workId|integer(int64)|false|none||采摘作业编号|
|employeeId|integer(int64)|false|none||员工编号|
|scaleId|integer(int64)|false|none||电子秤编号|

<h2 id="tocS_WorkVO">WorkVO</h2>

<a id="schemaworkvo"></a>
<a id="schema_WorkVO"></a>
<a id="tocSworkvo"></a>
<a id="tocsworkvo"></a>

```json
{
  "id": 0,
  "produceId": 0,
  "startTime": 0,
  "endTime": 0,
  "dataValue": 0,
  "unit": 0,
  "createTime": 0,
  "updateTime": 0,
  "status": 0
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|id|integer(int64)|false|none||none|
|produceId|integer(int64)|false|none||none|
|startTime|integer(int64)|false|none||none|
|endTime|integer(int64)|false|none||none|
|dataValue|number|false|none||none|
|unit|integer(int32)|false|none||none|
|createTime|integer(int64)|false|none||none|
|updateTime|integer(int64)|false|none||none|
|status|integer(int32)|false|none||none|

<h2 id="tocS_WorkAssignmentsVO">WorkAssignmentsVO</h2>

<a id="schemaworkassignmentsvo"></a>
<a id="schema_WorkAssignmentsVO"></a>
<a id="tocSworkassignmentsvo"></a>
<a id="tocsworkassignmentsvo"></a>

```json
{
  "workId": 0,
  "employees": {
    "property1": "string",
    "property2": "string"
  }
}

```

作业分配信息

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|workId|integer(int64)|false|none||采摘作业编号|
|employees|object|false|none||分配的员工列表|
|» **additionalProperties**|string|false|none||none|

<h2 id="tocS_UserVO">UserVO</h2>

<a id="schemauservo"></a>
<a id="schema_UserVO"></a>
<a id="tocSuservo"></a>
<a id="tocsuservo"></a>

```json
{
  "id": 0,
  "uid": "string",
  "name": "string",
  "roles": "string",
  "createTime": 0,
  "updateTime": 0,
  "status": 0
}

```

用户信息

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|id|integer(int64)|false|none||用户编号|
|uid|string|false|none||用户身份证|
|name|string|false|none||员工姓名|
|roles|string|false|none||角色，以英文逗号分隔，比如 ROLE_EMPLOYEE,ROLE_ADMIN|
|createTime|integer(int64)|false|none||创建时间，毫秒级时间戳|
|updateTime|integer(int64)|false|none||更新时间，毫秒级时间戳|
|status|integer(int32)|false|none||状态，0 为禁用，1 为启用，2 为已删除|

<h2 id="tocS_MyAssignmentsVO">MyAssignmentsVO</h2>

<a id="schemamyassignmentsvo"></a>
<a id="schema_MyAssignmentsVO"></a>
<a id="tocSmyassignmentsvo"></a>
<a id="tocsmyassignmentsvo"></a>

```json
{
  "workIds": [
    0
  ]
}

```

员工被分配的作业

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|workIds|[integer]|false|none||采摘作业编号列表|

<h2 id="tocS_UserListVO">UserListVO</h2>

<a id="schemauserlistvo"></a>
<a id="schema_UserListVO"></a>
<a id="tocSuserlistvo"></a>
<a id="tocsuserlistvo"></a>

```json
{
  "userList": [
    {
      "id": 0,
      "uid": "string",
      "name": "string",
      "roles": "string",
      "createTime": 0,
      "updateTime": 0,
      "status": 0
    }
  ],
  "count": 0
}

```

用户列表

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|userList|[[UserVO](#schemauservo)]|false|none||[用户信息]|
|count|integer(int64)|false|none||none|

<h2 id="tocS_WorkListVO">WorkListVO</h2>

<a id="schemaworklistvo"></a>
<a id="schema_WorkListVO"></a>
<a id="tocSworklistvo"></a>
<a id="tocsworklistvo"></a>

```json
{
  "workList": [
    {
      "id": 0,
      "produceId": 0,
      "startTime": 0,
      "endTime": 0,
      "dataValue": 0,
      "unit": 0,
      "createTime": 0,
      "updateTime": 0,
      "status": 0
    }
  ],
  "count": 0
}

```

作业列表

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|workList|[[WorkVO](#schemaworkvo)]|false|none||none|
|count|integer(int64)|false|none||none|

<h2 id="tocS_ScaleListVO">ScaleListVO</h2>

<a id="schemascalelistvo"></a>
<a id="schema_ScaleListVO"></a>
<a id="tocSscalelistvo"></a>
<a id="tocsscalelistvo"></a>

```json
{
  "scaleList": [
    {
      "id": 0,
      "skey": "string",
      "model": "string",
      "maxCapacity": 0,
      "minCapacity": 0,
      "unit": 0,
      "verificationInterval": 0,
      "displayInterval": 0,
      "unitDv": 0,
      "protocol": 0,
      "createTime": 0,
      "updateTime": 0,
      "status": 0
    }
  ],
  "count": 0
}

```

电子秤列表

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|scaleList|[[ScaleVO](#schemascalevo)]|false|none||[电子秤信息]|
|count|integer(int64)|false|none||none|

<h2 id="tocS_RecordListVO">RecordListVO</h2>

<a id="schemarecordlistvo"></a>
<a id="schema_RecordListVO"></a>
<a id="tocSrecordlistvo"></a>
<a id="tocsrecordlistvo"></a>

```json
{
  "recordList": [
    {
      "id": 0,
      "workId": 0,
      "employeeId": 0,
      "scaleId": 0,
      "dataValue": 0,
      "dataErrorMargin": 0,
      "unit": 0,
      "dataTime": 0
    }
  ],
  "count": 0
}

```

称重记录列表

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|recordList|[[RecordVO](#schemarecordvo)]|false|none||[称重记录]|
|count|integer(int64)|false|none||none|

<h2 id="tocS_ProduceListVO">ProduceListVO</h2>

<a id="schemaproducelistvo"></a>
<a id="schema_ProduceListVO"></a>
<a id="tocSproducelistvo"></a>
<a id="tocsproducelistvo"></a>

```json
{
  "produceList": [
    {
      "id": 0,
      "name": "string",
      "createTime": 0,
      "updateTime": 0,
      "status": 0
    }
  ],
  "count": 0
}

```

农产品列表

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|produceList|[[ProduceVO](#schemaproducevo)]|false|none||[农产品]|
|count|integer(int64)|false|none||none|

<h2 id="tocS_UserWorkOutputVO">UserWorkOutputVO</h2>

<a id="schemauserworkoutputvo"></a>
<a id="schema_UserWorkOutputVO"></a>
<a id="tocSuserworkoutputvo"></a>
<a id="tocsuserworkoutputvo"></a>

```json
{
  "name": "string",
  "workId": 0,
  "produceName": "string",
  "dataValue": 0,
  "unit": "string"
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|name|string|false|none||none|
|workId|integer(int64)|false|none||none|
|produceName|string|false|none||none|
|dataValue|number|false|none||none|
|unit|string|false|none||none|

<h2 id="tocS_ProduceAnnualOutputVO">ProduceAnnualOutputVO</h2>

<a id="schemaproduceannualoutputvo"></a>
<a id="schema_ProduceAnnualOutputVO"></a>
<a id="tocSproduceannualoutputvo"></a>
<a id="tocsproduceannualoutputvo"></a>

```json
{
  "name": "string",
  "year": 0,
  "dataValue": 0,
  "unit": "string"
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|name|string|false|none||none|
|year|integer(int32)|false|none||none|
|dataValue|number|false|none||none|
|unit|string|false|none||none|

<h2 id="tocS_ProduceWorkOutputVO">ProduceWorkOutputVO</h2>

<a id="schemaproduceworkoutputvo"></a>
<a id="schema_ProduceWorkOutputVO"></a>
<a id="tocSproduceworkoutputvo"></a>
<a id="tocsproduceworkoutputvo"></a>

```json
{
  "name": "string",
  "workId": 0,
  "dataValue": 0,
  "unit": "string"
}

```

### Attribute

|Name|Type|Required|Restrictions|Title|Description|
|---|---|---|---|---|---|
|name|string|false|none||none|
|workId|integer(int64)|false|none||none|
|dataValue|number|false|none||none|
|unit|string|false|none||none|


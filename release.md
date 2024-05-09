
# Release 

```
 [ AnalisisRepositoryImpl.lookup Command failed with error 292 (QueryExceededMemoryLimitNoDiskUseAllowed): 'Executor error during find command :: caused by :: Sort exceeded memory limit of 104857600 bytes, but did not opt in to external sorting. Aborting operation. Pass allowDiskUse:true to opt in.' on server localhost:27017. The full response is {"ok": 0.0, "errmsg": "Executor error during find command :: caused by :: Sort exceeded memory limit of 104857600 bytes, but did not opt in to external sorting. Aborting operation. Pass allowDiskUse:true to opt in.", "code": 292, "codeName": "QueryExceededMemoryLimitNoDiskUseAllowed"}]

`


[https://stackoverflow.com/questions/26375017/mongo-error-when-using-aggregation-sort-exceeded-memory-limit`](https://stackoverflow.com/questions/26375017/mongo-error-when-using-aggregation-sort-exceeded-memory-limit)


[Sort exceeded memory limit of 33554432 bytes error even after adding index](https://www.mongodb.com/community/forums/t/sort-exceeded-memory-limit-of-33554432-bytes-error-even-after-adding-index/239630)


Vishnu_Ramana Can you modify the aggregation operation to include the “allowDiskUse:true” option when invoking the aggregation.


 Can you modify the aggregation operation to include the “allowDiskUse:true” option when invoking the aggregation. in java driver

solución 

allowDiskUse:true” option

https://www.mongodb.com/community/forums/t/java-driver-allowdiskuse-true/218985

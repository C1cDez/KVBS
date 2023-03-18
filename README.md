## KVBS
Key-Value Binary System
API for working with kvbs-byte-code

<h1>API using</h1>

Create Compound KVBS-Object:
```
//Create compound object  
CompoundKVBS compound = new CompoundKVBS();  
  
//Put some data  
compound.putInteger("someInteger", 123);  
compound.putString("someName", "John");  
compound.putFloat("someLength", 49.32f);  
```

Create Array KVBS-Object:
```
//Create array object  
ArrayKVBS<KVBS.BooleanKVBS> booleanArray = new ArrayKVBS<>();  
  
//Put some data to array  
booleanArray.add(new KVBS.BooleanKVBS(true));  
booleanArray.add(new KVBS.BooleanKVBS(false));
```

<h1>Input-Output</h1>
Write bytes to file:

```
KvbsIO.write(someCompoundKVBS, new File(someFilePath));
```

Read bytes from file:

```
CompoundKVBS compound = KvbsIO.read(new File(someFile));
```

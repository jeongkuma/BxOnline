/**
 * 
 */

var sayHello = function(name) {
  print('Hello, ' + name + '!');
  return 'hello from javascript';
};


var jsonConvert = function(jsonObj, toJsonFormat) {
	print('원본 데이터 : jsonObj : ' +  jsonObj);
	print('원본 데이터 : toJsonFormat : ' +  toJsonFormat);
	var jsonObjToJSON =  JSON.parse(jsonObj);
	var jsonObjToJSONFormat =  JSON.parse(toJsonFormat);
	print('json 변환 정보 : jsonObjToJSON : ' + JSON.stringify(jsonObjToJSON));
	print('json 변환 정보 : jsonObjToJSONFormat : ' + JSON.stringify(jsonObjToJSONFormat));
	
	jsonObjToJSONFormat.home_code = jsonObjToJSON.sso_key;
	
	print('json 변환 정보 : jsonObjToJSONFormat : ' + JSON.stringify(jsonObjToJSONFormat));
	
	return jsonObjToJSONFormat;
}

var convertValueSample = function(param) {
	  return 'Hello, ' + param + '!';
}

var convertArg0 = function() {
	  return 'convertArg0 !';
}

var convertArg1 = function(param1) {
	  return 'convertArg1, ' + param1 + '!';
}

var convertArg2 = function(param1, param2) {
	  return 'convertArg2, ' + param1 + ',' + param2 + '!';
}
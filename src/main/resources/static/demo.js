const crypto = require('crypto-js');
const fs = require('fs');
const privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJccOdYyOLf69H9zQ11xR1gLUAf7PYVF1FOKmL1koF1F3O3bBkNQiKlwH+Q2H8aGtRX+AJx6j3ztuT5an9QTCbHi59U8F4w0g+nBoUlJG79AW/OAszdNnUlr7D7WwBnJeLBgn2d9QfA0+nQ+KpPZGrmUcIUnDk/CA+CeeUl2v+sRAgMBAAECgYB2tAuTAZygmhjazZbLnqLmvOw/eNNZEAO9JJDbpmJ7IthsWLPQyUCxqYzD3uiTMVqrTf747SYugygTnWkUVWg1BQ5JQh/Frf7ESRN6wwR06fp0Rk78LkI8LRyc6PfGayiXqz8NtdbEy1Pg/J2889jv2Zgzc647m40EUCo/ltjPqQJBAOst5uAez7E+xVdlU/bX3DaOgzR/3zEvJbNy8Fu9VfKatjwu07XLDlBduL8BvnGe6WKNhwFrxk3PztyVs7AOk58CQQCkfPw1/uItLphTzIhmFwOtJV+BnrrzUIpWssy/RXsJIA8bkKVWhie1P+6VNuNrSxAzHqEKWMbYy9WLCc5HioNPAkEAnOgyKoPEFECKD4Y2X/GjJe8tUMCj27/WCoT8ImkPR967CSpA7AB/G1V8Zku2kT3x/mPomCUc2Ft2a6uhiCwhhwJAB4/rdHwMX/FldWzQ1Ii4VYyDUI1AoRER2xyLRzvlhSzhJO5Ie6rdRnry+A8282bXDtKYqsYcFjmAzsybnDRlBwJBAOqj68+xfXiTOvYN7jAAhSt7xFRQYgWspaH81d3zjxY8FH/Pi7RyGb1tEw+XdnjryanHxcQRJXnBA8Xgpsz0m34="; 		//rsa私钥
const publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCXHDnWMji3+vR/c0NdcUdYC1AH+z2FRdRTipi9ZKBdRdzt2wZDUIipcB/kNh/GhrUV/gCceo987bk+Wp/UEwmx4ufVPBeMNIPpwaFJSRu/QFvzgLM3TZ1Ja+w+1sAZyXiwYJ9nfUHwNPp0PiqT2Rq5lHCFJw5PwgPgnnlJdr/rEQIDAQAB";
const str = 'abcd';

// 公钥加密，密钥解密
const publicEncodeData = crypto.publicEncrypt(publicKey, Buffer.from(str)).toString('base64');
console.log("encode: ", publicEncodeData);
const privateDecodeData = crypto.privateDecrypt(privateKey, Buffer.from(publicEncodeData.toString('base64'), 'base64'));
console.log("decode: ", privateDecodeData.toString())

// 密钥加密，公钥解密
/*
const privateEncodeData = crypto.privateEncrypt(privateKey, Buffer.from(str)).toString('base64');
console.log("encode: ", privateEncodeData);
const publicDecodeData = crypto.publicDecrypt(privateKey, Buffer.from(privateEncodeData.toString('base64'), 'base64'));
console.log("decode: ", publicDecodeData.toString())*/

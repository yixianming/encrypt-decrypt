import CryptoJS from 'crypto-js';
export default {
    data(){
        return {
            aes_key: 'xOwvIiP19Bn4XcQ8',
        }
    },
    mounted() {
        let walletAddress = 'TAfcPpLAUdy28u7nzFQjds39XSYJ9qA1mE'
        let timestamp = Date.parse(new Date());
        let aes_iv = walletAddress.substr(0, 16);
        let str = 'addr=' + walletAddress + '&method=/Index/sign_test&timestamp=' + timestamp;
        let encrypt_data = this.aes_encrypt(str, aes_iv);
        console.log('加密后：' + encrypt_data)
        let decrypt_data = this.aes_decrypt(encrypt_data, aes_iv);
        console.log('解密后：' + decrypt_data)
    },
    onLoad() {

    },
    methods: {
        // 加密
        aes_encrypt: function(plainText, aes_iv) {
            var encrypted = CryptoJS.AES.aes_encrypt(plainText,aes_iv);

            /*var encrypted = CryptoJS.AES.encrypt(plainText, CryptoJS.enc.Utf8.parse(this.aes_key), {
                iv: CryptoJS.enc.Utf8.parse(aes_iv)
            });*/
            return CryptoJS.enc.Base64.stringify(encrypted.ciphertext);
        },
        // 解密
        aes_decrypt: function(ciphertext, aes_iv) {
            var decrypted = CryptoJS.AES.aes_decrypt(ciphertext,aes_iv);
          /*  var decrypted = CryptoJS.AES.decrypt(ciphertext, CryptoJS.enc.Utf8.parse(this.aes_key), {
                iv: CryptoJS.enc.Utf8.parse(aes_iv)
            });*/
            return decrypted.toString(CryptoJS.enc.Utf8);
        },

    }
}
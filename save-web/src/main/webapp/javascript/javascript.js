	//Evita um erro na manipulação de chamadas ao AJAX para o IE9
	if(Sarissa._SARISSA_IS_IE && navigator.userAgent.toLowerCase().indexOf("msie 9") > -1) {
		window.XMLSerializer = function(){};
		window.XMLSerializer.prototype.serializeToString=function(oNode){return oNode.xml;};
	}
	
	// Function para teste 
	function teste(){
		alert('Teste funtion richJS');
	}

	// [ unused ]
	function refreshPage(){
		setTimeout('location.reload();', 1000);
	}

	/* 
	 * Masks
	 */
	function mask(o,f){
	    v_obj=o;
	    v_fun=f;
	    setTimeout("execmask()",1);
	}

	function execmask(){
	    v_obj.value=v_fun(v_obj.value);
	}

	/* Mascara que so permite numeros no campo [ 1234567890 ].
	 * Forma de uso no evento :[ mask(this,justNumber) ].
	 */
	function justNumber(v){
	    return v.replace(/\D/g,"");
	}
	
	/* Mascara para um campo de telefone [ (xx)xxxx-xxxx ]-->
	 * Forma de uso no evento :[ mask(this,telephone) ].
	 */ 
	function telephone(v){
	    v=v.replace(/\D/g,"");                
	    v=v.replace(/^(\d\d)(\d)/g,"($1) $2");
	    v=v.replace(/(\d{4})(\d)/,"$1-$2");
	    return v;
	}
	
	/* Mascara para um campo de cpf [ xxx.xxx.xxx-xx ]-->
	 * Forma de uso no evento :[ mask(this,cpf) ].
	 */ 
	function cpf(v){
	    v=v.replace(/\D/g,"");               
	    v=v.replace(/(\d{3})(\d)/,"$1.$2");  
	    v=v.replace(/(\d{3})(\d)/,"$1.$2");      
	                                             
	    v=v.replace(/(\d{3})(\d{1,2})$/,"$1-$2"); 
	    return v;
	}
	
	/* Mascara para um campo de cep [ xxxxx-xxx ]-->
	 * Forma de uso no evento :[ mask(this,cep) ].
	 */ 
	function cep(v){
	    v=v.replace(/D/g,"");                
	    v=v.replace(/^(\d{5})(\d)/,"$1-$2"); 
	    return v;
	}
	
	/* Mascara para um campo de cnpj [ xx.xxx.xxx/xxxx-xx ]-->
	 * Forma de uso no evento :[ mask(this,cnpj) ].
	 */ 
	function cnpj(v){
	    v=v.replace(/\D/g,"");                           
	    v=v.replace(/^(\d{2})(\d)/,"$1.$2");             
	    v=v.replace(/^(\d{2})\.(\d{3})(\d)/,"$1.$2.$3"); 
	    v=v.replace(/\.(\d{3})(\d)/,".$1/$2");           
	    v=v.replace(/(\d{4})(\d)/,"$1-$2");              
	    return v;
	}
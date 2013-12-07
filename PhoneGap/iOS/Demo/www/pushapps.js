window.pushapps = {
	
	registerDevice: function (pushAppsToken, successCallback, errorCallback) {
		cordova.exec(
	            successCallback, // success callback function
	            errorCallback, // error callback function
	            'PushAppsPlugin', // mapped to our native Java class
	            'registerUser', // with this action name
	            [                  // and this array of custom arguments to create our entry
	                pushAppsToken
	            ]
	        );
	},
	
	unRegisterDevice: function (successCallback, errorCallback) {
		cordova.exec(
	            successCallback, // success callback function
	            errorCallback, // error callback function
	            'PushAppsPlugin', // mapped to our native Java class
	            'unRegisterUser', // with this action name
	            []
	        );
	},
		
	onDeviceRegistered: function (registrationId) {
		alert("PUSHAPPS: Registration to PushApps with registration ID: " + registrationId);
	},
	
	onDeviceUnRegistered: function () {
		alert("PUSHAPPS: Unregistration to PushApps completed");
	},
		
	messageReceive: function (messageParams) {        
        setTimeout(function(){ alert(JSON.parse(messageParams)["aps"]["alert"]); }, 2000);
        
	}
};

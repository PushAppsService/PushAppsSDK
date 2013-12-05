window.pushapps = {
	
	registerDevice: function (googleProjectId, pushAppsToken, successCallback, errorCallback) {
		cordova.exec(
	            successCallback, // success callback function
	            errorCallback, // error callback function
	            'PushAppsPlugin', // mapped to our native Java class
	            'registerUser', // with this action name
	            [{                  // and this array of custom arguments to create our entry
	                "googleProjectId": googleProjectId,
	                "appToken": pushAppsToken
	            }]
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
		window.pushapps.getDeviceId(function (deviceId) {
			console.log(deviceId);
		}, function (errorMessage) {
			console.log(errorMessage);
		});
	},
	
	onDeviceUnRegistered: function () {
		alert("PUSHAPPS: Unregistration to PushApps succeeded");
	},
		
	messageReceive: function (messageParams) {
		alert("PUSHAPPS: Received remote notification with message: " + JSON.parse(messageParams)["Message"]);
	},
	
	getDeviceId: function (successCallback, errorCallback) {
		cordova.exec(
	            successCallback, // success callback function
	            errorCallback, // error callback function
	            'PushAppsPlugin', // mapped to our native Java class
	            'getDeviceId', // with this action name
	            []
	        );
	}
};

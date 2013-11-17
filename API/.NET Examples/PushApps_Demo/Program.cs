using PushApps_Demo.Manager;
using PushApps_Demo.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PushApps_Demo
{
    class Program
    {
        const string SecretToken = "497D5441-B25D-45D8-937D-8D16B7505B9E";
        static void Main(string[] args)
        {
            //SendRegisterRequest();      //sends a register request
            //SendUnregisterRequest();    //sends an unregister request
            SendPushNotification();     //sends a push notification request
        }

        /**
         * Method is simulating a register request being sent to PushApps server.
         */
        public static void SendRegisterRequest()
        {
            Register_Demo register = new Register_Demo();
            register.SecretToken = SecretToken;
            register.PushToken = "This is my push token";
            register.DeviceId = "This is my device ID";
            register.DeviceType = Enums.DevicePlatform.Android;     //can be changed to iOS
            register.CustomId = "This is my custom ID";
            register.OSVersion = "alpha";
            register.SDKVersion = "1.3";
            register.AppVersion = "1";
            register.TimeZone = 120;
            ActionsManager.SendRegisterRequest(register);   // will send the registeration request to our server.
        }

        /**
         * Method is simulating an unregister request being sent to PushApps server.
        */
        public static void SendUnregisterRequest()
        {
            Unregister_Demo unregister = new Unregister_Demo();
            unregister.SecretToken = SecretToken;
            unregister.DeviceId = "This is my device ID";
            ActionsManager.SendUnregisterRequest(unregister);
        }

        /**
         * Method is simulating a push notification request being sent to PushApps server.
         * including validations that needs to be done before sending notification.
        */
        public static void SendPushNotification()
        {
            PushNotification_Demo notification = new PushNotification_Demo();
            notification.SecretToken = SecretToken;
            notification.Message = "Hello World !";
            notification.Type = Enums.NotificationType.Immediate;      // Currently the only option available in the remote API.
            ActionsManager.SendPushNotificationRequest(notification);
        }

    }
}

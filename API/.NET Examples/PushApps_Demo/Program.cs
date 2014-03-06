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
        const string SecretToken = "THIS IS MY SECRET TOKEN";
        static void Main(string[] args)
        {
            //SendRegisterRequest();      //sends a register request
            //SendUnregisterRequest();    //sends an unregister request
            SendPushNotification();     //sends a push notification request
            //GetAllApplicationUsers();   //sends a request to get all devices by specific parameters.
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
         * Pay attention that only 1 of the audience option is allowed: either By Platforms OR Devices OR customIds OR DeviceIds.
        */
        public static void SendPushNotification()
        {
            PushNotification_Demo notification = new PushNotification_Demo();
            notification.SecretToken = SecretToken;
            notification.Message = "Hello World";

            //  an empty list will send to all available platforms.
               notification.Platforms = new List<Enums.DevicePlatform>();
               notification.Platforms.Add(Enums.DevicePlatform.Android);   // in case you want to choose only one platform and not send all
               notification.Platforms.Add(Enums.DevicePlatform.iOS);       // in case you want to choose only one platform and not send all

           

               /* in case you want to send to custom devices list.
                * notification.Devices = new List<Device>();
                * Device User = new Device();
                * User.DeviceType = Enums.DevicePlatform.Android; // can be also Enums.DevicePlatform.iOS
                * User.PushToken = "Enter Push Token Here";
                */

               /* in case you want to send to custom list of Device ids
                * notification.DeviceIds = new List<string>();
                * notification.DeviceIds.Add("30010501023");
                * notification.DeviceIds.Add("44557015744");
                */

               /* in case you want to send to a list of Custom ids you provided eariler.
                * notification.CustomIds = new List<string>();
                * notification.CustomIds.Add("IosDevice1");
                * notification.CustomIds.Add("AndroidDevice10");
                */

            /*
             * Platforms specific features, Null in sound means no sound will be sent causing device to get silent notification.
             */ 
            notification.PlatformFeatures = new PlatforeFeatures();
            notification.PlatformFeatures.AndroidTitle = "Some Android Title";
            //notification.PlatformFeatures.AndroidSound = "sound file without extension";
            notification.PlatformFeatures.iOSBadge = 1;     //can be changed to every number.
            //notification.PlatformFeatures.iOSSound = "sound file without extension";

            ActionsManager.SendPushNotificationRequest(notification);
        }

        public static void GetAllApplicationUsers()
        {
            GetDevice_Demo Devices = new GetDevice_Demo();
            Devices.Amount = 500;        // if not sent, we use default setting which currently is 1000
            Devices.Index = 0;           // pick devices from the first row
            Devices.DeviceType = Enums.DevicePlatform.iOS;  //if not supplied, returns all types.
            Devices.SecretToken = SecretToken;
            ActionsManager.SendGetDevicesRequest(Devices);
        }

    }
}

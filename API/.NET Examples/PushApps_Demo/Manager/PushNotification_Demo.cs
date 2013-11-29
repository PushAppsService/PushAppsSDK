using PushApps_Demo.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PushApps_Demo.Manager
{
    public class PushNotification_Demo
    {
        public string SecretToken { get; set; }
        public string Message { get; set; }
        public Enums.NotificationType Type { get; set; }             // Currently need to insert 1 (Immediate)
        public string Link { get; set; }                             //optional.
        public List<Enums.DevicePlatform> Platforms { get; set; }        //optional , if not having platforms, sends out to all Application's platform
        public string CustomJson { get; set; }                       //optional.
        public string CustomJsonKey { get; set; }                    //optional.
        public List<Device> Devices { get; set; }                        //optional.
        public PlatforeFeatures PlatformFeatures { get; set; }
        //If Devices array has devices the message will be sent to those user and to those users ONLY.
        //If you want to send to the application's users, please make sure this array is empty or null.

        public bool validate()
        {
            if (string.IsNullOrEmpty(this.SecretToken) || string.IsNullOrEmpty(this.Message))
            {   // if secret token and message are empty , request cannot be sent at all.
                Console.WriteLine("Mandatory field is missing in Push Notification.");
                return false;
            }
            else    // if secret token and message are OK
            {
                if (this.Devices != null && this.Devices.Count > 0)    // if we configured devices instead of sending to all application users
                {
                    foreach (Device device in this.Devices)
                    {
                        if (!device.validate())     //now we validating all devices, if one of them is bad we stop the procedure
                        {
                            Console.WriteLine("One of the custom devices in Push Notification is not configured well.");
                            return false;
                        }
                    }
                    return true;        //all configured devices are OK , we can continue
                }
                else
                {
                    return true;    // if no devices, all we need is secret token and message, which was provided currectly.
                }
            }
        }
    }
}

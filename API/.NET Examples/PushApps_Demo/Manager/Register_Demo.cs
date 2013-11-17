using PushApps_Demo.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PushApps_Demo.Manager
{
    public class Register_Demo
    {
        public string SecretToken { get; set; }
        public string PushToken { get; set; }
        public string DeviceId { get; set; }
        public Enums.DevicePlatform DeviceType { get; set; }    // 1 - Android, 2 - iOS.
        public string CustomId { get; set; }                    //optional.
        public string OSVersion { get; set; }                   //optional.
        public string SDKVersion { get; set; }                  //optional.
        public string AppVersion { get; set; }                  //optional.
        public int TimeZone { get; set; }                       //optional, in minutes from UTC. -> Scheduled coming soon..

        public bool validate()
        {
            if (string.IsNullOrEmpty(this.SecretToken) || string.IsNullOrEmpty(this.PushToken) || string.IsNullOrEmpty(this.DeviceId)
                || (this.DeviceType != Enums.DevicePlatform.Android && this.DeviceType != Enums.DevicePlatform.iOS))
            {
                Console.WriteLine("Mandatory field is missing in Register Request");
                return false;
            }
            return true;
        }
    }
}

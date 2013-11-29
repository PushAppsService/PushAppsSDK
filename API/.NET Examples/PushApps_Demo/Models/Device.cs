using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PushApps_Demo.Models
{
    public class Device
    {
        public string PushToken;
        public Enums.DevicePlatform DeviceType { get; set; }
        public int TimeZone { get; set; }   // if TimeZone is null it means the user's time is same as UTC, Scheduled coming soon..

        public bool validate()
        {       // mandatory fields must be configured, PushToken and Device type needs to be ligal
            return (!string.IsNullOrEmpty(this.PushToken) && Enum.IsDefined(typeof(Enums.NotificationType), DeviceType));
        }
    }
}

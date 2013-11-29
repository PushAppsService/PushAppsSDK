using PushApps_Demo.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PushApps_Demo.Manager
{
    public class GetDevice_Demo
    {
        public string SecretToken { get; set; }                     //Mandatory.
        public int Amount { get; set; }                             //Optional, how many devices.
        public int Index { get; set; }                              //Optional, from what line. we limit the requests, You can send the last row index you receive to get more devices.
        public Enums.DevicePlatform DeviceType { get; set; }        //Optional, returns all types if not supplied.


        public bool validate()
        {
            return (!string.IsNullOrEmpty(SecretToken));
        }
    }
}

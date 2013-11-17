using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PushApps_Demo.Manager
{
    public class Unregister_Demo
    {
        public string DeviceId { get; set; }
        public string SecretToken { get; set; }

        public bool validate()
        {   // Mandatory fields must be configured 
            if (!string.IsNullOrEmpty(this.DeviceId) && !string.IsNullOrEmpty(this.SecretToken))
            {
                return true;
            }
            else 
            {//both of the parameters should be filled.
                Console.WriteLine("Mandatory field is missing in Unregister Request");
                return false;
            }
        }
    }
}

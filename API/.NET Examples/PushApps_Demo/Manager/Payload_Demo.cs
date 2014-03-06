using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PushApps_Demo.Manager
{
    public class Payload_Demo
    {
        public string Message { get; set; }     
        public string Link { get; set; }            //optional.
        public string CustomJsonKey { get; set; }   //optional.
        public string CustomJson { get; set; }      //optional.
        public string SecretToken { get; set; }      

    }
}

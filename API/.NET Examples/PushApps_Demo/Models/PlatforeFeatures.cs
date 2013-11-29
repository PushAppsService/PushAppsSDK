using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PushApps_Demo.Models
{
    public class PlatforeFeatures
    {                   //Platform features is optional, if not set we use the default values.
        public string iOSSound { get; set; }            //default to 'default'
        public int iOSBadge { get; set; }               //default to 1
        public string AndroidTitle { get; set; }        //default to null
        public string AndroidSound { get; set; }        //default to 'default'
    }
}

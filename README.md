##Introduction to this project##
Julius is a library providing basic interfaces that can be used in any application associated with code judging, including the online judge.

##Environment requirements##
**OS** Linux 3.x

**JRE** 1.7 or higher

##Supported sandboxes##
Currently two different sandboxes are supported.

**SimpleSandbox** SimpleSandbox is a tiny sandbox by the author, which supports time and memory limit, time and memory measure, chroot. However, it has so far been unsafe because it does not support system call monitoring.

**Isolate** See [Isolate project page](https://github.com/cms-dev/isolate).

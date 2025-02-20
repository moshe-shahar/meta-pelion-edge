SUMMARY = "Linux microPlatform image running Pelion Edge"

require recipes-samples/images/lmp-image-common.inc

require recipes-samples/images/lmp-feature-factory.inc
require recipes-samples/images/lmp-feature-wireguard.inc
require recipes-samples/images/lmp-feature-docker.inc
require recipes-samples/images/lmp-feature-wifi.inc
require recipes-samples/images/lmp-feature-ota-utils.inc
require recipes-samples/images/lmp-feature-sbin-path-helper.inc

IMAGE_FEATURES[validitems] += "tools-debug tools-sdk"
IMAGE_FEATURES += "package-management ssh-server-openssh"
#IMAGE_LINGUAS = "en-us

IMAGE_OVERHEAD_FACTOR = "2"

DEPENDS += "deviceos-users"

CORE_IMAGE_BASE_INSTALL += " \
    kernel-modules \
    networkmanager-nmtui \
    git \
    vim \
    rng-tools \
    haveged \
    packagegroup-core-full-cmdline-utils \
    packagegroup-core-full-cmdline-extended \
    packagegroup-core-full-cmdline-multiuser \
"

CORE_OS = " \
openssh \
packagegroup-core-boot \
packagegroup-core-full-cmdline \
"

#mbed-edge-core is currently dependant on deviceos-users "developer"
PELION_BASE_REQUIRED = " \
deviceos-users \
virtual/mbed-edge-core \
identity-tool \
path-set \
pelion-version \
"
PELION_BASE_OPTIONAL = " \
mbed-fcc \
"

PELION_SYSTEMS_MANAGEMENT = "\
edge-proxy \
maestro \
devicedb \
info-tool \
relay-term \
fluentbit \
"

PELION_PROTOCOL_TRANSLATION = " \
mbed-edge-examples \
"

PELION_CONTAINER_ORCHESTRATION = " \
kubelet \
edge-proxy \
"

PELION_TESTING = " \
git \
panic \
"

IMAGE_INSTALL += " \
${CORE_OS} \
${PELION_BASE_REQUIRED} \
${PELION_BASE_OPTIONAL} \
${PELION_PROTOCOL_TRANSLATION} \
${PELION_SYSTEMS_MANAGEMENT} \
${PELION_CONTAINER_ORCHESTRATION} \
${PELION_TESTING} \
${MACHINE_EXTRA_RRECOMMENDS} \
"

set_local_timezone() {
    ln -sf /usr/share/zoneinfo/EST5EDT ${IMAGE_ROOTFS}/etc/localtime
}

disable_bootlogd() {
    echo BOOTLOGD_ENABLE=no > ${IMAGE_ROOTFS}/etc/default/bootlogd
}

ROOTFS_POSTPROCESS_COMMAND += " \
    set_local_timezone ; \
    disable_bootlogd ; \
"

export IMAGE_BASENAME = "console-image-lmp"

# MoreChunkLoadersMod

## 简介

此Mod为Minecraft 1.16.5添加了一些模拟1.14-的区块加载方式

目前支持的有:

1.活塞(粘性与非粘性)

2.漏斗(未锁定)

## 活塞

当活塞伸出或收回 (执行 onSyncedBlockEvent() 时)，在伸出的活塞头所在的区块添加一个 piston 加载票: 等级为32 持续4gt

## 漏斗

未锁定的漏斗被tick时，若指向了一个非自身所在的区块，在指向的区块添加一个 hopper 加载票: 等级为31 持续1gt

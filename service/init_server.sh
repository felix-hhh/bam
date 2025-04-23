#!/bin/bash

# 提示用户输入 Docker 用户名
read -p "请输入你的 Docker 用户名: " username

# 提示用户输入 Docker 密码，输入时不显示密码
read -s -p "请输入你的 Docker 密码: " password
echo

# 登录 Docker
docker login -u "$username" -p "$password" registry.cn-hangzhou.aliyuncs.com

# 检查登录是否成功
if [ $? -eq 0 ]; then
    echo "Docker 登录成功"

    # 定义要拉取的 Docker 镜像列表
    images=(
        "registry.cn-hangzhou.aliyuncs.com/kelaker/mysql:8.4.2"
        "registry.cn-hangzhou.aliyuncs.com/kelaker/redis:5-alpine"
        "registry.cn-hangzhou.aliyuncs.com/kelaker/openjdk:17"
        "registry.cn-hangzhou.aliyuncs.com/kelaker/nginx:1.25.2-alpine"
    )

    # 循环遍历镜像列表并拉取每个镜像
    for image in "${images[@]}"
    do
        echo "正在拉取镜像: $image"
        docker pull "$image"
        if [ $? -eq 0 ]; then
            echo "镜像 $image 拉取成功"
            new_image="${image##*/}"
            docker tag "$image" "$new_image"
            if [ $? -eq 0 ]; then
                echo "镜像 $image 重命名为 $new_image 成功"
                # 移除原镜像
                docker rmi "$image"
                if [ $? -eq 0 ]; then
                    echo "原镜像 $image 移除成功"
                else
                    echo "原镜像 $image 移除失败"
                fi
            else
                echo "镜像 $image 重命名失败"
            fi
        else
            echo "镜像 $image 拉取失败"
        fi
    done
else
    echo "Docker 登录失败，请检查用户名和密码"
fi
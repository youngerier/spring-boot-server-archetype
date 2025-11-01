#!/bin/bash

# 显示使用说明
show_usage() {
    echo "Usage: $0 [options]"
    echo "Options:"
    echo "  -h, --help     显示帮助信息"
    echo "  -t, --type     指定构建类型 (admin|member|all)"
    echo "Example:"
    echo "  $0 -t admin    # 只构建admin镜像"
    echo "  $0 -t member   # 只构建member镜像"
    echo "  $0 -t all      # 构建所有镜像"
}

# 参数解析
BUILD_TYPE="all"
while [[ $# -gt 0 ]]; do
    case $1 in
        -h|--help)
            show_usage
            exit 0
            ;;
        -t|--type)
            if [[ "$2" =~ ^(admin|member|all)$ ]]; then
                BUILD_TYPE="$2"
                shift 2
            else
                echo "错误: 构建类型必须是 admin、member 或 all"
                show_usage
                exit 1
            fi
            ;;
        *)
            echo "错误: 未知参数 $1"
            show_usage
            exit 1
            ;;
    esac
done

# 获取Git短版本号
GIT_COMMIT_ID=$(git rev-parse --short HEAD)

# 构建函数
build_admin() {
    echo "Building admin image with commit ID: ${GIT_COMMIT_ID}"
    docker build \
      --build-arg GIT_COMMIT_ID=${GIT_COMMIT_ID} \
      -t ping-admin:${GIT_COMMIT_ID} \
      -f bootstrap/ping-app-admin/Dockerfile .
    docker tag ping-admin:${GIT_COMMIT_ID} ping-admin:latest
}

build_member() {
    echo "Building member image with commit ID: ${GIT_COMMIT_ID}"
    docker build \
      --build-arg GIT_COMMIT_ID=${GIT_COMMIT_ID} \
      -t ping-member:${GIT_COMMIT_ID} \
      -f bootstrap/ping-app-member/Dockerfile .
    docker tag ping-member:${GIT_COMMIT_ID} ping-member:latest
}

# 根据构建类型执行构建
case $BUILD_TYPE in
    admin)
        build_admin
        ;;
    member)
        build_member
        ;;
    all)
        build_admin
        build_member
        ;;
esac

echo "Build completed successfully!"
echo "Images built:"
[[ $BUILD_TYPE =~ ^(admin|all)$ ]] && echo "  - ping-admin:${GIT_COMMIT_ID}" && echo "  - ping-admin:latest"
[[ $BUILD_TYPE =~ ^(member|all)$ ]] && echo "  - ping-member:${GIT_COMMIT_ID}" && echo "  - ping-member:latest"
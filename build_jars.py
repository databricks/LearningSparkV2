from multiprocessing import Pool
import os

def build_jar_files(dir):
    print(f"Building jars for {dir}")
    if (dir == "chapter6"):
        cmd = f"cd {dir}/java;sbt package clean"
    else:
        cmd = f"cd {dir}/scala;sbt package clean"
    exit_status = os.system(cmd)
    if exit_status:
        os._exists(exit_status)


if __name__ == '__main__':
    chapters = ["chapter2", "chapter3", "chapter4", "chapter6"]
    with Pool(5) as p:
        p.map(build_jar_files, chapters)
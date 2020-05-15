from multiprocessing import Pool
import os

def build_jar_files(dir):
    '''
    Function to build Scala and Java jars for the each relevant chapter
    :param dir: top-level chapterX directory
    :return:
    '''
    print(f"Building jars for {dir}")
    if (dir == "chapter6"):
        cmd = f"cd {dir}/java && sbt clean package"
    else:
        cmd = f"cd {dir}/scala && sbt clean package"
    exit_status = os.system(cmd)
    if exit_status:
        os._exit(exit_status)
    return exit_status


if __name__ == '__main__':
    # List of chapters to build Scala or Java jars
    chapters = ["chapter2", "chapter3", "chapter6", "chapter7"]
    with Pool(4) as p:
        p.map(build_jar_files, chapters)

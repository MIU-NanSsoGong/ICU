import cv2
import os
import shutil
icu_path = os.environ['icu_path']
def extract(number):
    user_num = number
    vidcap = cv2.VideoCapture(icu_path  + 'server/upload_video.avi')
    count = 0
    ret = True

    if not os.path.exists(icu_path + "ML/images/" + user_num):
                os.makedirs(icu_path + "ML/images/" + user_num)
    else:
        shutil.rmtree(icu_path + "ML/images/" + user_num)

    while(ret):
        ret, image = vidcap.read()
    
        if(int(vidcap.get(1)) % 2 == 0):
            #print('Saved frame number : ' + str(int(vidcap.get(1))))
            cv2.imwrite(os.environ['icu_path'] + "ML/images/" + user_num + "/%s_%d.jpg" % (user_num, count), image)
            #print('Saved frame%d.jpg' % count)
            count += 1

    vidcap.release()

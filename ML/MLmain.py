import recoder
import extracter
import detecter
import trainer
import tester
import os

icu_path = os.environ['icu_path']

def MLseq(number):
    extracter.extract(number)
    detecter.main(icu_path + 'ML/images/', icu_path + 'ML/faces', 300)
    trainer.main()

def check_visitor():
    #recoder.main()
    tester.main()
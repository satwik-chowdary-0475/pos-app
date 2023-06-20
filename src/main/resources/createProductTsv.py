import csv

with open('/Users/satwikchowdary/Desktop/pos/src/main/resources/product.tsv', 'wt') as out_file:
    tsv_writer = csv.writer(out_file, delimiter='\t')
    tsv_writer.writerow(['name', 'brandCategory','mrp'])
    tsv_writer.writerow(['dairymilk', '1','100.23'])
    tsv_writer.writerow(['dabur honey', '2','342'])

//
//  main.cpp
//  Trie_country
//
//  Created by ohazyi on 2017/7/10.

#include <iostream>
#include <cstdio>
#include <cstring>
#include <string>
#include <cstdlib>
#include <fstream>
#include <algorithm>
#include <string>
#include <map>

using namespace std;

const int max_cou = 1000;
const int max_rec = 1e6;

FILE *in, *in2, *out1, *out2, *tojava;

map<string, string> mp;
map<string, string>::iterator it;
map<string, int> v;
map<string, int> v_count;

string value, v_t, value_prime;
string str, cou[max_cou][110], rec[max_rec], cou_lower[max_cou][110];
string::size_type pos;
string s1, s2, s3, s4, s5, s6, s7, s8, s9;
string s_after, s_after_lower;

int nummm[max_cou];
int n;
int tot_all, tot_cou, tot_rec;
int tot,num[95000000],b[9500000][41],first[80010],now[80010],next[80010],cnt[150010];
char cou_id[9500000][4];

bool not_used[80010];
bool exist;

struct nnnodee{
    int cnt;
    string s;
}nat[max_cou];

struct node{
    int i, cnt;
    string s;
}nation[max_cou];

bool cmp(nnnodee a, nnnodee b){
    return (a.cnt>b.cnt)||(a.cnt==b.cnt&&a.s<b.s);
}

string Standardized(string str){
    string res = "";
    for (auto i : str){
        if (isalpha(i)){
            res += tolower(i)-'a';
        }
    }
    return res;
}

string Stand_remove_other(string str){
    string res = "";
    for (auto i : str){
        if (isalpha(i)){
            res += i;
        }
    }
    return res;
}

string extract(string str){
    string res = "";
    int i, j;
    if (str[0]!='\"'){
        for (i = str.size()-1; i >= 0; --i)
            if (str[i] != ',' && str[i] !='\r')
                break;
        return str.substr(0,i+1);
    }
    j = 0;
    while (str[j]=='\"')
        j++;
    for (i = j; i < str.size(); ++i){
        if (str[i] == '\"'){
            
            break;
        }
    }
    
    return str.substr(j,i-1-(j-1));
}

bool dfs(int start)
{
    int l, p;
    l = str.size();
    p = 0;
    for (int i = 0; i < l; ++i){
        //printf("%d ",int(str[i]));
        p = b[p][str[i]];
        if (p==0){
            break;
        }
    }
    if (p==0 || num[p]==0){
        return false;
    }
    
    tot_all++;
//    cout << value << " Is From " << cou_id[p] << endl;
    it = mp.find(cou_id[p]);
    if (it != mp.end()){
//        cout << value << " : " << mp[cou_id[p]] << endl;
        cout << extract(value_prime) << " : " << mp[cou_id[p]] << endl;
        
        char s[1010];
        memset(s,0,sizeof(s));
        string ou;
        ou = extract(value_prime);
        if (ou.size()==0)
            return false;
        
        strncpy(s, ou.c_str(), ou.length());//字符串转字符数组
        if (ou.size()==0)
            return false;
        fprintf(tojava,"%s",s);
        fprintf(tojava, "::::::");
        memset(s,0,sizeof(s));
        ou = mp[cou_id[p]];
        
        if (ou=="Haiti"){
            int hg;
            hg = 9988;
        }
        
        strncpy(s,ou.c_str(),ou.length());//字符串转字符数组
        fprintf(tojava,"%s\n",s);
        
        v[cou_id[p]]++;
        return true;
    }
//    cout <<"!!!"<< cou_id[p];
    return false;
}

bool execute(int i,int j, int k, int type){
    string s_c, s_after_t;
    pos = s_after.find(cou[j][k]);
    bool flag = false;
    
    if (pos == s_after.npos){//如果是国家，那么大小写匹配都可以
        if (type == 0){
            pos = s_after_lower.find(cou_lower[j][k]);
            if (pos != s_after_lower.npos){
                cout <<"$$$$$$" << s_after << endl;
                flag = true;
            }
        }
    }else{
        flag = true;
    }
    
    if (flag == true){
        
//       cout <<i << " "<< j << " " << k<<" " <<  s_after << " Is From City " << cou[j][k] << " From Country " << nation[j].s<< endl;
//        nation[j].cnt++;
//        exist = true;
//        return true;
        
        s_c = nation[j].s;
        transform(s_c.begin(), s_c.end(), s_c.begin(), ::tolower);
        
        it = mp.find(s_c);
        if (it != mp.end()){
//            cout<< i<<" "<< j<<" " << k<<" ";
            cout << extract(rec[i]) << " : " << mp[s_c] << endl;
            
            char s[1010];
            memset(s,0,sizeof(s));
            string ou;
            ou = extract(rec[i]);
            if (ou.size()==0)
                return false;
            strncpy(s,ou.c_str(),ou.length());//字符串转字符数组
            fprintf(tojava,"%s",s);
            fprintf(tojava, "::::::");
            memset(s,0,sizeof(s));
            ou = mp[s_c];
            if (ou.size()==0)
                return false;
            strncpy(s, ou.c_str(), ou.length());//字符串转字符数组
            fprintf(tojava,"%s\n",s);
            
            v[s_c]++;
            
            nation[j].cnt++;
            exist = true;
            return true;
        }else{
//            cout << "!!!" << s_c << " " << cou[j][k]<<endl;
        }
    }
    return false;
}
int main(int argc, const char * argv[]) {
    
    in = fopen("worldcitiespop.txt", "r");
    out1 = fopen("unknown.txt", "w");
    out2 = fopen("unknown_part1.txt","w");
    in2 = fopen("map.txt", "r");
    tojava = fopen("tojava.txt", "w");
    
    bool flag;
    int p, ptr, l, tot_comma, offset;
    char s[510], t[510], last_cou[510], last_cit[510], s_1[510], s_2[510], t_t[510];
    tot = 0;
    fgets(s,100,in);
    int now= 0, h;
    

    int pos_comma;
    while (fgets(s,500,in2)!=NULL){
        l = strlen(s);
        for (int i = 0; i < l; ++i){
            if (s[i]==','){
                pos_comma = i;
                break;
            }
        }
        string str1(&s[0],&s[pos_comma]);
        string str2(&s[pos_comma+1],&s[l]);
        str2 = str2.substr(0,2);
        str2+="\0";
//        cout << str1 << " " << str2<< " "<< str1.size()<< endl;
        mp[str2] = str1;
    }
    
    while (fgets(s,500,in)!=NULL){
        now++;
        flag = false;
        tot_comma = 0;
        offset = 0;
        if (strcmp(s,"")==0)
        {
            continue;
        }
        l = strlen(s);
        ptr = 0;
        for (int i = 0; i < l; ++i){
            if (s[i]==','){
                tot_comma++;
                t_t[ptr] = '\0';
                t[ptr] = '\0';
            }else{
                if (isalpha(s[i])){
                    t_t[ptr++] = tolower(s[i]);
                    //t[ptr++] = tolower(s[i])-'a';
                }
            }
            if (tot_comma == 1 && !flag){
                //strcpy(s1, t);
                strcpy(s_1, t_t);
                ptr = 0;
                //offset = i+1;
                flag = true;
            }
            if (tot_comma == 2){
                //strcpy(s2, t);
                strcpy(s_2, t_t);
                break;
            }
            
        }
        
        
        if (strcmp(s_1, last_cou)==0 && strcmp(s_2, last_cit)==0){
            continue;
        }
        
        strcpy(last_cou, s_1);
        strcpy(last_cit, s_2);
        l = strlen(s_2);
        p = 0;
        
        if (s_1[0]=='z'&&s_1[1]=='w'&&s_2[0]=='x'){
            int h;
            h=1;
        }
        for (int j = 0; j < l; ++j){
            h = s_2[j]-'a';
            //printf("%d ", s2[i]);
            if (b[p][h]==0){
                b[p][h] = ++tot;
            }
            p = b[p][h];
        }
        
        v_count[s_1]++;
//        printf("%d   %d\n", v_count[s_1], v_count[cou_id[p]]);
        if (num[p] == 0 || v_count[s_1] >= v_count[cou_id[p]]){
            num[p] = 1;
            strcpy(cou_id[p], s_1);
        }
    }
    cout << now << endl;
    fclose(in);
    
    tot_rec = tot_cou = tot_all = 0;
    
    freopen("country&state.xml", "r", stdin);
     
    while (cin >> s1 >> n >> s3){
        if (n==0){
            break;
        }
        n-=1000;
        cin >> s4 >> s5 >> s6;
        cin >> s7 >> s8 >> s9;
        
        cou_lower[n][1] = cou[n][1] = s5;
        transform(cou_lower[n][1].begin(), cou_lower[n][1].end(), cou_lower[n][1].begin(), ::tolower);
        
        nummm[n] = 1;
        nation[n].s = s8;//s5;
        nation[n].i = tot_cou;
        nation[n].cnt = 0;
        tot_cou = n;
    }
   
    
    n=228;//美国
    nummm[n]++;
    cou_lower[n][nummm[n]] = cou[n][nummm[n]] = "USA";
    nummm[n]++;
    cou_lower[n][nummm[n]] = cou[n][nummm[n]] = "America";
    
    n=29;//巴西
    nummm[n]++;
    cou_lower[n][nummm[n]] = cou[n][nummm[n]] = "Brasil";
    
    n=177;//俄罗斯
    nummm[n]++;
    cou_lower[n][nummm[n]] = cou[n][nummm[n]] =  "RussianFederation";
    nummm[n]++;
    cou_lower[n][nummm[n]] = cou[n][nummm[n]] =  "Russian";
    
//    while (cin >> s1 >> s2 >> s3){
//        cin >> s4 >> s5 >> s6;
//        cin >> s7 >> n >> s9;
//        n-=1000;
//        //        cout << n << endl;
//        nummm[n]++;
//        cou_lower[n][nummm[n]] = cou[n][nummm[n]] = s2;
//        transform(cou_lower[n][nummm[n]].begin(), cou_lower[n][nummm[n]].end(), cou_lower[n][nummm[n]].begin(), ::tolower);
//    }

    
    int tot;
    int not_exist = 0;
    int other_language;
    int matches;
    
    ifstream infile("export.csv");
    
    while(infile.good()){
        //.csv文件用","作为分隔符
        getline(infile,value,'\n');
        rec[++tot_rec] = value;
    }
    
    /******************第一部分*********************/
    for (int i = 1; i <= tot_rec; ++i){
        s_after_lower = s_after = Stand_remove_other(rec[i]);
        transform(s_after_lower.begin(), s_after_lower.end(), s_after_lower.begin(), ::tolower);
        
        exist = false;
        for (int j = 1; j <= tot_cou; ++j){//先判断是不是包含某个国家的名字，即国家名字优先，以防出现某个国家是另外某个城市的名字的这样的情况
            //网上下的数据没有索引为71这个国家，奇怪的东西
            if (j==71)
                continue;
            
            if (execute(i, j, 1, 0))
                break;
        }
        if (!exist)
        for (int j = 1; j <= tot_cou; ++j){
            for (int k = 2; k <= nummm[j]; ++k){
                
                if (j==24&&k==6){
                    int hhh;
                    hhh = 1;
                }
                
                if (execute(i,j,k, 1))
                    break;
            }
            if (exist){
                break;
            }
            
        }
        if (exist){
            tot_all++;
        }else{
            //cout << s_after << "   Unknown" << endl;
//            not_exist++;
            string outt;
            if (s_after.size()> 1)
            {
                memset(s,0,sizeof(s));
                outt = extract(rec[i]);
                strncpy(s, outt.c_str(), outt.length());//字符串转字符数组
                fprintf(out2,"%s\n",s);
            }
            not_used[i] = true;

        }
    }
    
    cout << "-----------************----------" << endl;
    
    /******************第二部分*********************/
    
    int last;
    memset(s,0,sizeof(s));
    
    for (int iii = 1; iii <= tot_rec; ++iii){
        if (!not_used[iii])
            continue;

        if (iii==46563){
            int jjjk;
            jjjk= 1;
            
        }
       
        
        value = rec[iii];
        value_prime = value;
        value = Stand_remove_other(value);
        
        if (value=="London(ish)"){
            int jjjjj;
            jjjjj = 1;
        }
        
        str = Standardized(value);
        
        if (dfs(0))
            continue;
        l = value.size();
        if (isupper(value[l-1])&&isupper(value[l-2]))//针对“HustonAB”这种格式{
        {
            str = Standardized(value.substr(0,l-2));
            
            if (dfs(0)){
                continue;
            }
        }
       
        bool sign = false;
        last = 0;
        v_t = value[0];
        int ptr_v = 1;
        int i = 1;
        while (i <= l){
            if (isupper(value[i])){
//                v_t[ptr_v] = '\0';
                v_t+="\0";
                if (ptr_v>1){
                    str = Standardized(v_t);
                    if (dfs(0)){
                        sign = true;
                        break;
                    }
                }
                last = ptr_v;
                v_t = value[i++];
                ptr_v = 0;
                
            }
            v_t += value[i++];
            ptr_v++;
        }
        if (sign)
            continue;
        if (value.size()<=1)
            continue;
        memset(s,0,sizeof(s));
        string outt;
        outt = extract(value_prime);
        strncpy(s,outt.c_str(),outt.length());//字符串转字符数组
        fprintf(out1,"%s\n",s);
//        not_used[tot_rec] = true;
        not_exist++;
    }
    
//    cout << tot_rec << endl;
//    cout << tot_all << endl;
    
    
    cout << "Not Exist: " << not_exist << endl;
    cout << "Solved: " << tot_all << endl;

    
    int zzzyyy = 0;
    for (auto pr : v){
        //cout << pr.first <<" "<< pr.second << endl;
        nat[++zzzyyy].s = mp[pr.first];
        nat[zzzyyy].cnt = pr.second;
    }
    sort(nat+1,nat+zzzyyy+1,cmp);
    for (int i = 1; i <= zzzyyy; ++i){
        cout << nat[i].s << " " << nat[i].cnt << endl;
    }
    return 0;
}

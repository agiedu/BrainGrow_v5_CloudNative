import {useEffect,useMemo,useState} from 'react';

const API=(import.meta.env.VITE_API_URL||'http://localhost:8080').replace(/\/$/,'');
type Page='home'|'login'|'register'|'reset'|'learn'|'history'|'admin';
type Exercise={id:string;title:string;type:string;difficulty:string;question:string;answer?:string};
type RecordItem={id:string;exerciseId:string;score:number;createdAt:string};
type User={id:string;email:string;age:number;role:string;enabled:boolean;createdAt:string};
type Stats={users:number;enabledUsers:number;exercises:number;learningRecords:number};

async function api(path:string,opts:RequestInit={}) {
 const token=localStorage.getItem('token');
 const headers=new Headers(opts.headers); headers.set('Content-Type','application/json'); if(token)headers.set('Authorization',`Bearer ${token}`);
 const r=await fetch(API+path,{...opts,headers}); const text=await r.text(); let data:any={}; try{data=text?JSON.parse(text):{}}catch{data={message:text}};
 if(r.status===401){localStorage.removeItem('token');localStorage.removeItem('role');throw new Error('登录已过期，请重新登录。')}
 if(!r.ok)throw new Error(data.error||data.message||'请求失败'); return data;
}
function msg(e:unknown){return e instanceof Error?e.message:'请求失败';}

export default function App(){
 const [page,setPage]=useState<Page>(()=>localStorage.getItem('token')?(localStorage.getItem('role')==='ADMIN'?'admin':'learn'):'home');
 const [email,setEmail]=useState(''); const [password,setPassword]=useState(''); const [age,setAge]=useState(''); const [code,setCode]=useState(''); const [notice,setNotice]=useState('');
 const [exercises,setExercises]=useState<Exercise[]>([]); const [selected,setSelected]=useState<Exercise|null>(null); const [answer,setAnswer]=useState(''); const [result,setResult]=useState<any>(null); const [history,setHistory]=useState<RecordItem[]>([]);
 const [users,setUsers]=useState<User[]>([]); const [stats,setStats]=useState<Stats|null>(null);
 const [busy,setBusy]=useState(false);

 useEffect(()=>{if(page==='learn')loadExercises(); if(page==='history')loadHistory(); if(page==='admin')loadAdmin();},[page]);
 async function loadExercises(){try{setExercises(await api('/api/exercises'))}catch(e){setNotice(msg(e))}}
 async function loadHistory(){try{setHistory(await api('/api/learning/me'))}catch(e){setNotice(msg(e))}}
 async function loadAdmin(){try{const [u,s]=await Promise.all([api('/api/admin/users'),api('/api/admin/stats')]);setUsers(u);setStats(s)}catch(e){setNotice(msg(e))}}
 function logout(){localStorage.clear();setPage('home');setNotice('');}
 async function login(e:any){e.preventDefault();setBusy(true);try{const d=await api('/api/auth/login',{method:'POST',body:JSON.stringify({email,password})});localStorage.setItem('token',d.token);localStorage.setItem('role',d.role);setPage(d.role==='ADMIN'?'admin':'learn');setNotice('登录成功')}catch(e){setNotice(msg(e))}finally{setBusy(false)}}
 async function register(e:any){e.preventDefault();setBusy(true);try{await api('/api/auth/register',{method:'POST',body:JSON.stringify({email,password,age:Number(age)})});setNotice('注册成功，请登录。');setPage('login')}catch(e){setNotice(msg(e))}finally{setBusy(false)}}
 async function sendCode(){setBusy(true);try{await api('/api/auth/send-code',{method:'POST',body:JSON.stringify({email})});setNotice('如果账户存在，验证码已发送，请检查邮箱。')}catch(e){setNotice(msg(e))}finally{setBusy(false)}}
 async function reset(e:any){e.preventDefault();setBusy(true);try{await api('/api/auth/reset-password',{method:'POST',body:JSON.stringify({email,code,newPassword:password})});setNotice('密码重置成功，请登录。');setPage('login')}catch(e){setNotice(msg(e))}finally{setBusy(false)}}
 async function submit(){if(!selected)return;setBusy(true);try{const d=await api('/api/learning/submit',{method:'POST',body:JSON.stringify({exerciseId:selected.id,answer})});setResult(d);setAnswer('')}catch(e){setNotice(msg(e))}finally{setBusy(false)}}
 async function toggleUser(u:User){try{await api(`/api/admin/users/${u.id}/status?enabled=${!u.enabled}`,{method:'PUT'});loadAdmin()}catch(e){setNotice(msg(e))}}
 async function changeRole(u:User){const role=u.role==='STUDENT'?'PARENT':u.role==='PARENT'?'ADMIN':'STUDENT';try{await api(`/api/admin/users/${u.id}/role?role=${role}`,{method:'PUT'});loadAdmin()}catch(e){setNotice(msg(e))}}

 if(page==='home')return <Shell><section className="hero"><span className="badge">7–18岁儿童学习训练</span><h1>BrainGrow</h1><p>用有趣、循序渐进的练习培养逻辑推理与记忆能力。</p><div className="actions"><button onClick={()=>setPage('login')}>登录</button><button className="secondary" onClick={()=>setPage('register')}>创建账户</button></div></section><section className="cards"><Card t="逻辑推理" d="数字规律与问题解决，帮助孩子建立结构化思考。"/><Card t="记忆训练" d="短时记忆练习，逐步提高注意力与信息保持能力。"/><Card t="安全账户" d="密码加密、JWT认证、验证码找回密码与权限隔离。"/></section></Shell>;

 if(page==='login'||page==='register'||page==='reset')return <Shell><div className="auth"><button className="link" onClick={()=>setPage('home')}>← 返回首页</button><h2>{page==='login'?'欢迎回来':page==='register'?'创建 BrainGrow 账户':'找回密码'}</h2><p className="muted">{page==='register'?'年龄范围：7–18岁':page==='reset'?'通过邮箱验证码设置新密码':'登录后开始训练。'}</p><form onSubmit={page==='login'?login:page==='register'?register:reset}>
 <label>邮箱<input type="email" value={email} onChange={e=>setEmail(e.target.value)} required/></label>
 {page==='register'&&<label>年龄<input type="number" min="7" max="18" value={age} onChange={e=>setAge(e.target.value)} required/></label>}
 {page==='reset'&&<label>验证码<div className="row"><input value={code} onChange={e=>setCode(e.target.value)} placeholder="6位验证码" required/><button type="button" className="secondary" onClick={sendCode} disabled={busy}>发送验证码</button></div></label>}
 <label>{page==='reset'?'新密码':'密码'}<input type="password" minLength={8} maxLength={128} value={password} onChange={e=>setPassword(e.target.value)} required/></label>
 <button disabled={busy}>{busy?'处理中…':page==='login'?'登录':page==='register'?'注册':'重置密码'}</button></form>{notice&&<Notice text={notice}/>}<div className="switch">{page==='login'?<><button className="link" onClick={()=>setPage('register')}>创建账户</button><button className="link" onClick={()=>setPage('reset')}>忘记密码？</button></>:<button className="link" onClick={()=>setPage('login')}>返回登录</button>}</div></div></Shell>;

 return <Shell><nav><strong>BrainGrow</strong><div><button className={page==='learn'?'nav active':'nav'} onClick={()=>setPage('learn')}>训练</button><button className={page==='history'?'nav active':'nav'} onClick={()=>setPage('history')}>我的记录</button>{localStorage.getItem('role')==='ADMIN'&&<button className={page==='admin'?'nav active':'nav'} onClick={()=>setPage('admin')}>管理后台</button>}<button className="nav" onClick={logout}>退出</button></div></nav>{notice&&<Notice text={notice}/>}
 {page==='learn'&&<Learn exercises={exercises} selected={selected} setSelected={(x)=>{setSelected(x);setResult(null);setAnswer('')}} answer={answer} setAnswer={setAnswer} submit={submit} result={result} busy={busy}/>}
 {page==='history'&&<History data={history}/>}
 {page==='admin'&&<Admin users={users} stats={stats} toggle={toggleUser} role={changeRole}/>}
 </Shell>
}
function Shell({children}:any){return <main className="app">{children}<footer>BrainGrow V5 · 安全学习平台</footer></main>}
function Card({t,d}:any){return <article className="card"><h3>{t}</h3><p>{d}</p></article>}
function Notice({text}:{text:string}){return <div className="notice">{text}</div>}
function Learn({exercises,selected,setSelected,answer,setAnswer,submit,result,busy}:any){
 return <section><div className="sectionHead"><div><span className="badge">学习中心</span><h2>今天练一练</h2><p className="muted">选择一道题，提交后系统会自动记录成绩。</p></div></div><div className="exerciseGrid">{exercises.map((e:Exercise)=><button key={e.id} className={`exercise ${selected?.id===e.id?'selected':''}`} onClick={()=>setSelected(e)}><b>{e.title}</b><span>{e.type==='memory'?'记忆':'逻辑'} · {e.difficulty}</span></button>)}</div>{selected&&<article className="question"><span className="badge">{selected.type==='memory'?'记忆训练':'逻辑训练'}</span><h3>{selected.question}</h3><input value={answer} onChange={e=>setAnswer(e.target.value)} placeholder="输入你的答案"/><button onClick={submit} disabled={busy||!answer.trim()}>{busy?'提交中…':'提交答案'}</button>{result&&<div className={result.correct?'success':'failure'}>{result.correct?'回答正确！':'这次不对。'} 正确答案：<b>{result.correctAnswer}</b> · 得分 {result.score}</div>}</article>}</section>
}
function History({data}:{data:RecordItem[]}){return <section><h2>我的学习记录</h2>{data.length===0?<div className="empty">还没有记录。完成第一道题吧！</div>:<div className="tableWrap"><table><thead><tr><th>练习</th><th>得分</th><th>时间</th></tr></thead><tbody>{data.map(r=><tr key={r.id}><td>{r.exerciseId.slice(0,8)}…</td><td>{r.score}</td><td>{new Date(r.createdAt).toLocaleString()}</td></tr>)}</tbody></table></div>}</section>}
function Admin({users,stats,toggle,role}:any){return <section><div className="sectionHead"><div><span className="badge">ADMIN</span><h2>管理后台</h2></div></div>{stats&&<div className="stats"><Card t="用户" d={String(stats.users)}/><Card t="启用账户" d={String(stats.enabledUsers)}/><Card t="题目" d={String(stats.exercises)}/><Card t="学习记录" d={String(stats.learningRecords)}/></div>}<div className="tableWrap"><table><thead><tr><th>邮箱</th><th>年龄</th><th>角色</th><th>状态</th><th>操作</th></tr></thead><tbody>{users.map((u:User)=><tr key={u.id}><td>{u.email}</td><td>{u.age}</td><td>{u.role}</td><td>{u.enabled?'正常':'已禁用'}</td><td><button className="small" onClick={()=>toggle(u)}>{u.enabled?'禁用':'启用'}</button><button className="small secondary" onClick={()=>role(u)}>切换角色</button></td></tr>)}</tbody></table></div></section>}

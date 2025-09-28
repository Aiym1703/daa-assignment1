param(
  [string]$algo = "all",  # all | mergesort | quicksort | select | closest
  [int]$n = 50000,
  [int]$trials = 3
)

# Build runnable JAR
mvn -q -DskipTests package
$jar = Get-ChildItem target/*-SNAPSHOT.jar | Select-Object -First 1
New-Item -ItemType Directory -Force -Path results | Out-Null

# Determine which algos to run
$algos = @()
if ($algo -eq "all") {
  $algos = @("mergesort","quicksort","select","closest")
} else {
  $algos = @($algo)
}

foreach ($a in $algos) {
  $out = "results/$a.csv"
  if ($a -eq "closest") {
    # closest is heavier; cap n at 20000
    $nEff = [Math]::Min($n, 20000)
    java -jar $jar --algo $a --n $nEff --trials $trials --out $out
  } else {
    java -jar $jar --algo $a --n $n --trials $trials --out $out
  }
  Write-Host "CSV written to $out"
}
